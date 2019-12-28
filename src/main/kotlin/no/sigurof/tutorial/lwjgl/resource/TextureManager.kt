package no.sigurof.tutorial.lwjgl.resource

import no.sigurof.tutorial.lwjgl.engine.Loader

class TextureManager {
    companion object {
        private val texturesByName = mapOf(
            "stall" to "src/main/resources/model/stall/stall-texture.png",
            "default" to "src/main/resources/model/default-texture.png"
        )

        fun get(name: String): Int {
            return Loader.loadTexture(
                texturesByName[name] ?: error("Couldn't find a texture associated with the name $name")
            )
        }

    }
}