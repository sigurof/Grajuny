package no.sigurof.grajuny.resource

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30

class BillboardResource(
    val vao: Int,
    private val vertexCount: Int = 4
) {

    fun render() {
        GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, vertexCount)
    }

    fun activate(){
        GL30.glBindVertexArray(vao)
    }

    fun deactivate() {
        GL30.glBindVertexArray(0)
    }

}