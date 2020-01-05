package no.sigurof.tutorial.lwjgl.resource

import no.sigurof.tutorial.lwjgl.shaders.settings.impl.BillboardShaderSettings
import org.lwjgl.opengl.GL30

class TexturedBillboardResource(
    private val tex: Int,
    vao: Int,
    vertexCount: Int = 4
) : ResourceGl<BillboardShaderSettings> {
    private val billboardResource: BillboardResource = BillboardResource(vao, vertexCount)
    override fun render() {
        billboardResource.render()
    }

    override fun using(shader: BillboardShaderSettings, function: () -> Unit) {
        GL30.glActiveTexture(GL30.GL_TEXTURE0)
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, tex)
        shader.loadUseTexture(true)
        function()
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0)
    }

    override fun getVao(): Int {
        return billboardResource.getVao()
    }
}