package no.sigurof.tutorial.lwjgl.resource

import no.sigurof.tutorial.lwjgl.entity.Camera
import org.lwjgl.opengl.GL30

class TexturedBillboardResource(
    private val tex: Int,
    vao: Int,
    vertexCount: Int = 4,
    camera: Camera
) : ResourceGl {
    private val billboardResource: BillboardResource = BillboardResource(vao, vertexCount, camera)
    override fun render() {
        billboardResource.render()
    }

    override fun prepare() {
        GL30.glActiveTexture(GL30.GL_TEXTURE0)
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, tex)
        billboardResource.render()
    }

    override fun getVao(): Int {
        return billboardResource.getVao()
    }
}