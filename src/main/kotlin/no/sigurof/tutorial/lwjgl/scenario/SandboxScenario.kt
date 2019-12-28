package no.sigurof.tutorial.lwjgl.scenario

import no.sigurof.tutorial.lwjgl.engine.DisplayManager
import no.sigurof.tutorial.lwjgl.entity.Camera
import no.sigurof.tutorial.lwjgl.entity.Entity
import no.sigurof.tutorial.lwjgl.entity.Light
import no.sigurof.tutorial.lwjgl.model.TexturedModel
import no.sigurof.tutorial.lwjgl.shaders.TextureShader
import no.sigurof.tutorial.lwjgl.utils.Maths
import no.sigurof.tutorial.lwjgl.utils.ORIGIN
import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.opengl.GL30
import kotlin.math.tan

class SandboxScenario private constructor(
    private val shader: TextureShader,
    private val camera: Camera,
    private val light: Light,
    private val entities: Map<TexturedModel, List<Entity>>,
    private val FOV: Float = 70f,
    private val NEAR_PLANE: Float = 0.1f,
    private val FAR_PLANE: Float = 1000f
) : Scenario {

    companion object {

        fun default(window: Long): SandboxScenario {
            val texturedModel = TexturedModel.Builder()
                .withModel("sphere")
                .withTexture("default")
                .withReflectivity(1f)
                .withShineDamper(1f)
                .build()
            val light = Light.With()
                .position(Vector3f(0f, 10f, 5f))
                .color(Vector3f(1f, 1f, 1f))
                .ambient(0.1f)
                .build()
            val shader = TextureShader()
            val camera = Camera.Builder()
                .at(Vector3f(0f, 0f, -3f))
                .lookingAt(ORIGIN)
                .inWindow(window)
                .withSpeed(1f)
                .build()
            val entities = mutableListOf<Entity>()
            entities.add(Entity(texturedModel, Vector3f(0f, 0f, 0f), Vector3f(0f, 0f, 0f), Vector3f(1f, 1f, 1f)))
            return SandboxScenario(shader, camera, light, orderEntitiesByModel(entities))
        }


        private fun orderEntitiesByModel(entities: List<Entity>): Map<TexturedModel, List<Entity>> {
            val entitiesByModel = mutableMapOf<TexturedModel, MutableList<Entity>>()
            for (entity in entities) {
                val entityModel = entity.texturedModel
                entitiesByModel.get(entityModel)?.add(entity) ?: run {
                    val newBatch = ArrayList<Entity>()
                    newBatch.add(entity)
                    entitiesByModel.put(entityModel, newBatch)
                }
            }
            return entitiesByModel
        }
    }

    override fun prepare() {
        uploadProjectionMatrix(shader)
    }


    override fun run() {
        camera.move()
        prepareFrame()
        shader.start()
        shader.loadLight(light)
        shader.loadViewMatrix(camera)
        render(entities, shader)
        shader.stop()
    }

    override fun cleanUp() {
        shader.cleanUp()
    }


    private fun uploadProjectionMatrix(shader: TextureShader) {
        val projectionMatrix = createProjectionMatrix()
        shader.start()
        shader.loadProjectionMatrix(projectionMatrix)
        shader.stop()
    }


    private fun createProjectionMatrix(): Matrix4f {
        val aspectRatio = DisplayManager.WIDTH.toFloat() / DisplayManager.HEIGHT.toFloat()
        val yScale = ((1f / tan(Math.toRadians(FOV / 2.0))) * aspectRatio).toFloat()
        val xScale = yScale / aspectRatio
        val frustumLength = FAR_PLANE - NEAR_PLANE

        val projectionMatrix = Matrix4f()
        projectionMatrix.m00(xScale)// = xScale
        projectionMatrix.m00(xScale)
        projectionMatrix.m11(yScale)
        projectionMatrix.m22(-(FAR_PLANE + NEAR_PLANE) / frustumLength)
        projectionMatrix.m23(-1f)
        projectionMatrix.m32(-(2 * NEAR_PLANE * FAR_PLANE) / frustumLength)
        projectionMatrix.m33(0f)
        return projectionMatrix
    }


    private fun render(entitiesByModel: Map<TexturedModel, List<Entity>>, shader: TextureShader) {
        for (model in entitiesByModel.keys) {
            prepareTexturedModel(model, shader)
            val entities = entitiesByModel.get(model)!!
            for (entity in entities) {
                prepareInstance(entity, shader)
                GL30.glDrawElements(GL30.GL_TRIANGLES, model.rawModel.vertexCount, GL30.GL_UNSIGNED_INT, 0)
            }
            unbindTexturedModel(shader)
        }
    }

    private fun unbindTexturedModel(shader: TextureShader) {
        for (attr in shader.boundAttribs) {
            GL30.glDisableVertexAttribArray(attr)
        }
        GL30.glBindVertexArray(0)
    }

    private fun prepareInstance(entity: Entity, shader: TextureShader) {
        val transformationMatrix = Maths.createTransformationMatrix(entity)
        shader.loadTransformationMatrix(transformationMatrix)
    }

    private fun prepareTexturedModel(texturedModel: TexturedModel, shader: TextureShader) {
        val rawModel = texturedModel.rawModel
        GL30.glBindVertexArray(rawModel.vao)
        for (attr in shader.boundAttribs) {
            GL30.glEnableVertexAttribArray(attr)
        }
        val texture = texturedModel.texture
        shader.loadSpecularValues(texture.shineDamper, texture.reflectivity)
        GL30.glActiveTexture(GL30.GL_TEXTURE0)
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, texture.tex)
    }

    private fun prepareFrame() {
        GL30.glEnable(GL30.GL_DEPTH_TEST)
        GL30.glEnable(GL30.GL_CULL_FACE)
        GL30.glCullFace(GL30.GL_BACK)
        GL30.glClearColor(0.2f, 0.3f, 0.1f, 1.0f)
        GL30.glClear(GL30.GL_COLOR_BUFFER_BIT or GL30.GL_DEPTH_BUFFER_BIT)
    }
}