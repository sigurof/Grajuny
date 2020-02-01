package no.sigurof.grajuny.resource

import no.sigurof.grajuny.shaders.settings.impl.BillboardShaderSettings
import org.lwjgl.opengl.GL11

class BillboardResource(
    override val vao: Int,
    private val vertexCount: Int = 4
) : ResourceGl<BillboardShaderSettings> {

    override fun render() {
        GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, vertexCount)
    }

    override fun activate(shader: BillboardShaderSettings){
        shader.loadUseTexture(false)
    }

    override fun deactivate(shader: BillboardShaderSettings) {
    }

}