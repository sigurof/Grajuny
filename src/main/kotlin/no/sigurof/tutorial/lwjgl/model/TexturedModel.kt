package no.sigurof.tutorial.lwjgl.model

import no.sigurof.tutorial.lwjgl.entity.Camera
import no.sigurof.tutorial.lwjgl.entity.Entity
import no.sigurof.tutorial.lwjgl.entity.Light
import no.sigurof.tutorial.lwjgl.mesh.MeshManager
import no.sigurof.tutorial.lwjgl.resource.TextureCreator
import no.sigurof.tutorial.lwjgl.shaders.TextureShader
import no.sigurof.tutorial.lwjgl.textures.Texture
import no.sigurof.tutorial.lwjgl.utils.Maths
import org.joml.Vector3f
import org.lwjgl.opengl.GL30

class TexturedModel private constructor(
    private val rawModel: RawModel,
    private val texture: Texture,
    private val shader: TextureShader,
    private var color: Vector3f
) : Model {

    override fun render(
        entities: List<Entity>,
        light: Light,
        camera: Camera,
        fov: Float,
        nearPlane: Float,
        farPlane: Float
    ) {
        // TODO Can probably be combined with last statement to create a context
        //  which returns the necessary shader object.
        shader.start()
        shader.bindVertAttrArrayAndVao(rawModel.vao)
        loadUniforms(light, camera, fov, nearPlane, farPlane)
        GL30.glActiveTexture(GL30.GL_TEXTURE0)
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, texture.tex)
        for (entity in entities) {
            shader.loadTransformationMatrix(Maths.createTransformationMatrix(entity))
            GL30.glDrawElements(GL30.GL_TRIANGLES, rawModel.vertexCount, GL30.GL_UNSIGNED_INT, 0)
        }
        shader.unbindVertAttrArrayAndVao()
        shader.stop()
    }

    override fun cleanShader() {
        shader.cleanUp()
    }

    private fun loadUniforms(light: Light, camera: Camera, fov: Float, nearPlane: Float, farPlane: Float) {
        shader.loadLight(light)
        shader.loadProjectionMatrix(Maths.createProjectionMatrix(fov, nearPlane, farPlane))
        shader.loadViewMatrix(Maths.createViewMatrix(camera))
        shader.loadSpecularValues(texture.shineDamper, texture.reflectivity)
        shader.loadColor(color)
    }

    data class Builder(
        private var rawModel: RawModel? = null,
        private var texName: String = "default",
        private var texShineDamper: Float = 1f,
        private var texReflectivity: Float = 1f,
        private var color: Vector3f = Vector3f(1f, 1f, 1f)
    ) {

        fun withTexture(name: String) = apply { this.texName = name }
        fun withModel(name: String) = apply { this.rawModel = MeshManager.get(name) }
        fun withReflectivity(reflectivity: Float) = apply { this.texReflectivity = reflectivity }
        fun withShineDamper(shineDamper: Float) = apply { this.texShineDamper = shineDamper }
        fun withColor(color: Vector3f) = apply { this.color = color }
        fun build(): TexturedModel {
            val texture = TextureCreator.get(texName, texShineDamper, texReflectivity)
            return TexturedModel(
                rawModel ?: error("Must have model to build textured model"),
                texture,
                TextureShader,
                color
            )
        }
    }
}