package no.sigurof.tutorial.lwjgl.resource

import no.sigurof.tutorial.lwjgl.engine.Loader

object TextureManager {

    private val sources = mapOf(
        "stall" to "src/main/resources/model/stall/stall-texture.png",
        "default" to "src/main/resources/model/default-texture.png"
    )

    private val textures = mutableMapOf<String, Int>()

    fun get(name: String): Int {
        textures[name] ?: let {
            textures[name] = Loader.loadTexture(
                sources[name] ?: error("Couldn't find a texture associated with the name $name")
            )
        }
        return textures[name]!!
    }

}