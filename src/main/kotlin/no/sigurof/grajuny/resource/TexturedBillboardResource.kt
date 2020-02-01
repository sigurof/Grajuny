package no.sigurof.grajuny.resource

import no.sigurof.grajuny.shaders.settings.impl.BillboardShaderSettings
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

    override val vao: Int
        get() = billboardResource.vao

    override fun activate(shader: BillboardShaderSettings) {
        GL30.glActiveTexture(GL30.GL_TEXTURE0)
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, tex)
        shader.loadUseTexture(true)
    }

    override fun deactivate(shader: BillboardShaderSettings) {
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0)
    }
}