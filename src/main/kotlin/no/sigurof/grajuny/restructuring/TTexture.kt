package no.sigurof.grajuny.restructuring

import no.sigurof.grajuny.restructuring.shader.interfaces.TextureShader
import org.lwjgl.opengl.GL30

class TTexture(private val tex: Int) {
    fun activate() {
        GL30.glActiveTexture(GL30.GL_TEXTURE0)
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, tex)
    }

    fun deactivate() {
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0)
    }

    fun render(basicShader: TextureShader){
        basicShader.loadUseTexture(true)
    }
}