package no.sigurof.tutorial.lwjgl.model

import no.sigurof.tutorial.lwjgl.mesh.MeshManager
import no.sigurof.tutorial.lwjgl.resource.TextureCreator
import no.sigurof.tutorial.lwjgl.textures.Texture

class TexturedModel private constructor(
    val rawModel: RawModel,
    val texture: Texture
) {
    data class Builder(
        private var rawModel: RawModel? = null,
        private var texName: String = "default",
        private var texShineDamper: Float = 1f,
        private var texReflectivity: Float = 1f
    ) {
        fun withTexture(name: String) = apply { this.texName = name }
        fun withModel(name: String) = apply { this.rawModel = MeshManager.get(name) }
        fun withReflectivity(reflectivity: Float) = apply { this.texReflectivity = reflectivity }
        fun withShineDamper(shineDamper: Float) = apply { this.texShineDamper = shineDamper }
        fun build(): TexturedModel {
            val texture = TextureCreator.get(texName, texShineDamper, texReflectivity)
            return TexturedModel(rawModel ?: error("Must have model to build textured model"), texture)
        }

    }


}