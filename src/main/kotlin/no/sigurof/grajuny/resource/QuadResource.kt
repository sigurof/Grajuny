package no.sigurof.grajuny.resource

import no.sigurof.grajuny.resource.mesh.Loader
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30
import org.lwjgl.opengl.GL30.glBindVertexArray

class QuadResource {

    private val vao: Int = Loader.createVao()
    private val vertexCount: Int = 6

    init {
        glBindVertexArray(vao)
        Loader.storeDataInAttributeList(
            0,
            2,
            listOf(
                listOf(-1f, -1f),
                listOf(1f, -1f),
                listOf(-1f, 1f),
                listOf(-1f, 1f),
                listOf(1f, -1f),
                listOf(1f, 1f)
            )
                .flatten()
                .toFloatArray()

        )
        Loader.storeDataInAttributeList(
            1,
            2,
            listOf(
                listOf(0f, 0f),
                listOf(1f, 0f),
                listOf(0f, 1f),
                listOf(0f, 1f),
                listOf(1f, 0f),
                listOf(1f, 1f)
            )
                .flatten()
                .toFloatArray()
        )
        glBindVertexArray(0)
    }

    fun render() {
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount)
    }

    fun activate() {
        GL30.glBindVertexArray(vao)
        for (attr in listOf(0, 1)) {
            GL30.glEnableVertexAttribArray(attr)
        }
    }

    fun deactivate() {
        for (attr in listOf(0, 1)) {
            GL30.glDisableVertexAttribArray(attr)
        }
        GL30.glBindVertexArray(0)
    }

}
