package no.sigurof.tutorial.lwjgl.resource

import no.sigurof.tutorial.lwjgl.textures.Texture

class TextureCreator {

    companion object {
        fun get(name: String, shineDamper: Float, reflectivity: Float): Texture {
            return Texture(TextureManager.get(name), shineDamper, reflectivity)
        }
    }
}