package no.sigurof.tutorial.lwjgl.resource

import no.sigurof.tutorial.lwjgl.entity.Camera
import no.sigurof.tutorial.lwjgl.shaders.settings.impl.BillboardShaderSettings
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30

class TexturedBillboardResource(
    private val vao: Int,
    private val vertexCount: Int = 4,
    private val camera: Camera,
    private val tex: Int
) : ResourceGl {
    override fun render() {
        GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, vertexCount)
    }

    override fun prepare() {
        GL30.glActiveTexture(GL30.GL_TEXTURE0)
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, tex)
        BillboardShaderSettings.loadCameraPos(camera.pos)
    }

    override fun getVao(): Int {
        return vao
    }
}