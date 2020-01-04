package no.sigurof.tutorial.lwjgl.resource

import no.sigurof.tutorial.lwjgl.entity.Camera
import no.sigurof.tutorial.lwjgl.shaders.settings.impl.BillboardShaderSettings
import org.lwjgl.opengl.GL30

class TexturedBillboardResource(
    private val tex: Int,
    vao: Int,
    vertexCount: Int = 4,
    camera: Camera
) : ResourceGl<BillboardShaderSettings> {
    private val billboardResource: BillboardResource = BillboardResource(vao, vertexCount, camera)
    override fun render() {
        billboardResource.render()
    }

    override fun prepare(shader: BillboardShaderSettings) {
        GL30.glActiveTexture(GL30.GL_TEXTURE0)
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, tex)
        billboardResource.prepare(shader)
    }

    override fun getVao(): Int {
        return billboardResource.getVao()
    }
}