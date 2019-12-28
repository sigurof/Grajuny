package no.sigurof.tutorial.lwjgl.engine

import no.sigurof.tutorial.lwjgl.entity.Camera
import no.sigurof.tutorial.lwjgl.entity.Entity
import no.sigurof.tutorial.lwjgl.entity.Light
import no.sigurof.tutorial.lwjgl.model.TexturedModel
import no.sigurof.tutorial.lwjgl.shaders.TextureShader
import no.sigurof.tutorial.lwjgl.utils.Maths
import org.joml.Matrix4f
import org.lwjgl.opengl.GL30

class Visualization {

    companion object {

        private const val FOV = 70f
        private const val NEAR_PLANE = 0.1f
        private const val FAR_PLANE = 1000f


        fun play(shader: TextureShader, camera: Camera, light: Light, entities: List<Entity>) {
            val entitiesByModel = orderEntitiesByModel(entities)
            uploadProjectionmatrix(shader)
            while (DisplayManager.isOpen()) {
                DisplayManager.eachFrameDo {
                    renderMain(shader, camera, light, entitiesByModel)
                }
            }

        }

        private fun uploadProjectionmatrix(shader: TextureShader) {
            val projectionMatrix = createProjectionMatrix()
            shader.start()
            shader.loadProjectionMatrix(projectionMatrix)
            shader.stop()
        }

        private fun renderMain(
            shader: TextureShader,
            camera: Camera,
            light: Light,
            entities: Map<TexturedModel, List<Entity>>
        ) {
            camera.move()
            prepare()
            shader.start()
            shader.loadLight(light)
            shader.loadViewMatrix(camera)
            render(entities, shader)
            shader.stop()
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

        private fun prepare() {
            GL30.glEnable(GL30.GL_DEPTH_TEST)
            GL30.glEnable(GL30.GL_CULL_FACE)
            GL30.glCullFace(GL30.GL_BACK)
            GL30.glClearColor(0.2f, 0.3f, 0.1f, 1.0f)
            GL30.glClear(GL30.GL_COLOR_BUFFER_BIT or GL30.GL_DEPTH_BUFFER_BIT)
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
}