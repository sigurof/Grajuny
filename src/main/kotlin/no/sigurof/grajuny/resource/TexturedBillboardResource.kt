package no.sigurof.grajuny.resource

import org.lwjgl.opengl.GL30

class TexturedBillboardResource(
    private val tex: Int,
    vao: Int,
    vertexCount: Int = 4
) : ResourceGl {
    private val billboardResource: BillboardResource = BillboardResource(vao, vertexCount)
    override fun render() {
        billboardResource.render()
    }

    override val vao: Int
        get() = billboardResource.vao

    override fun activate() {
        GL30.glBindVertexArray(vao)
        GL30.glActiveTexture(GL30.GL_TEXTURE0)
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, tex)
    }

    override fun deactivate() {
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0)
        GL30.glBindVertexArray(0)
    }
}