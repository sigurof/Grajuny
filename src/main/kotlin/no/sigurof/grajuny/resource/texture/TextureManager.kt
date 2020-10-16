package no.sigurof.grajuny.resource.texture

import de.matthiasmann.twl.utils.PNGDecoder
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL30
import java.nio.ByteBuffer

object TextureManager {

    private val sources = mapOf(
        "stall" to "/model/stall/stall-texture.png",
        "default" to "/model/default-texture.png",
        "earth512" to "/texture/earth512.png",
        "earth1024" to "/texture/earth1024.png",
        "earth8192" to "/texture/earth8192.png"
    )

    private val textures = mutableMapOf<String, Int>()

    fun get(name: String): Int {
        textures[name] ?: let {
            textures[name] = loadTexture(
                sources[name] ?: error("Couldn't find a texture associated with the name $name")
            )
        }
        return textures[name]!!
    }

    fun cleanUp() {
        for (tex in textures.values) {
            GL15.glDeleteTextures(tex)
        }
    }
}

private fun loadTexture(source: String): Int {
    val inputStream =  TextureManager::class.java.getResourceAsStream(source)
    val decoder = PNGDecoder(inputStream)
    val buffer: ByteBuffer = ByteBuffer.allocateDirect(3 * decoder.width * decoder.height)
    decoder.decode(buffer, decoder.width * 3, PNGDecoder.Format.RGB)
    buffer.flip()
    inputStream.close()

    val texture = GL15.glGenTextures()
    GL15.glBindTexture(GL15.GL_TEXTURE_2D, texture)
    GL15.glTexImage2D(
        GL15.GL_TEXTURE_2D, 0, GL15.GL_RGB, decoder.width, decoder.height, 0,
        GL15.GL_RGB, GL15.GL_UNSIGNED_BYTE, buffer
    )
    GL30.glGenerateMipmap(GL15.GL_TEXTURE_2D)
    GL15.glTexParameteri(GL15.GL_TEXTURE_2D, GL15.GL_TEXTURE_MIN_FILTER, GL15.GL_NEAREST)
    GL15.glTexParameteri(GL15.GL_TEXTURE_2D, GL15.GL_TEXTURE_MAG_FILTER, GL15.GL_NEAREST)
    return texture
}
