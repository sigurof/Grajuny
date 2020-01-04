package no.sigurof.tutorial.lwjgl.resource

import no.sigurof.tutorial.lwjgl.entity.Camera
import no.sigurof.tutorial.lwjgl.shaders.settings.impl.BillboardShaderSettings
import org.lwjgl.opengl.GL11

class BillboardResource(
    private val vao: Int,
    private val vertexCount: Int = 4,
    private val camera: Camera
) : ResourceGl {

    override fun render() {
        GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, vertexCount)
    }

    override fun prepare() {
        BillboardShaderSettings.loadCameraPos(camera.pos)
    }

    override fun getVao(): Int {
        return vao
    }
}