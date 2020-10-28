package no.sigurof.grajuny.resource.texture

import no.sigurof.grajuny.shader.interfaces.TextureShader
import org.lwjgl.opengl.GL30

class TextureRenderer(
    val textureResource: TextureResource
) {

    fun activate() {
        GL30.glActiveTexture(GL30.GL_TEXTURE0)
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, textureResource.tex)
    }

    fun deactivate() {
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0)
    }

    fun render(basicShader: TextureShader) {
        basicShader.loadUseTexture(true)
    }

    companion object {
        fun fromName(textureName: String): TextureRenderer {
            return TextureRenderer(
                textureResource = TextureManager.get(textureName)
            )
        }
    }
}