package no.sigurof.tutorial.lwjgl.engine

import no.sigurof.tutorial.lwjgl.entity.Camera
import no.sigurof.tutorial.lwjgl.entity.Entity
import no.sigurof.tutorial.lwjgl.entity.Light
import no.sigurof.tutorial.lwjgl.model.RawModel
import no.sigurof.tutorial.lwjgl.shaders.TextureShader
import no.sigurof.tutorial.lwjgl.utils.Maths
import org.joml.Matrix4f
import org.lwjgl.opengl.GL30.*

// TODO Dette lager avhengighet til hvilken shader man bruker...
class Renderer(
    private var shader: TextureShader,
    private var camera: Camera,
    private var light: Light
) {

    companion object {
        private val FOV = 70f
        private val NEAR_PLANE = 0.1f
        private val FAR_PLANE = 1000f
    }

    private val projectionMatrix = createProjectionMatrix()

    init {
        uploadProjectionmatrix()
    }

    // TODO kan potensielt brukes av en public fun changeProjection(fov, np, fp)
    private fun uploadProjectionmatrix() {
        shader.start()
        shader.loadProjectionMatrix(projectionMatrix)
        shader.stop()
    }


    fun prepare() {
        glEnable(GL_DEPTH_TEST)
        glClearColor(0.2f, 0.3f, 0.1f, 1.0f)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
    }

    fun render(model: RawModel) {
        glBindVertexArray(model.vao)
        glEnableVertexAttribArray(0)
        glDrawElements(GL_TRIANGLES, model.vertexCount, GL_UNSIGNED_INT, 0)
        glDisableVertexAttribArray(0)
        glBindVertexArray(0)
    }

    fun render(entity: Entity) {
        val texturedModel = entity.texturedModel
        val model = texturedModel.rawModel
        val texture = texturedModel.texture
        shader.start()
        glBindVertexArray(model.vao)
        for (attr in shader.boundAttribs) {
            glEnableVertexAttribArray(attr)
        }
        val transformationMatrix = Maths.createTransformationMatrix(entity)
        shader.loadTransformationMatrix(transformationMatrix)
        shader.loadViewMatrix(camera)
        shader.loadLight(light)
        shader.loadSpecularValues(texture.shineDamper, texture.reflectivity)
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, texture.tex)
        glDrawElements(GL_TRIANGLES, model.vertexCount, GL_UNSIGNED_INT, 0)
        for (attr in shader.boundAttribs) {
            glDisableVertexAttribArray(attr)
        }
        glBindVertexArray(0)
        shader.stop()
    }

    fun stop() {
        shader.cleanUp()
    }

    private fun createProjectionMatrix(): Matrix4f {
        val aspectRatio = DisplayManager.WIDTH.toFloat() / DisplayManager.HEIGHT.toFloat()
        val yScale = ((1f / Math.tan(Math.toRadians(FOV / 2.0))) * aspectRatio).toFloat()
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
}