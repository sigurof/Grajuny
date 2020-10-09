package no.sigurof.grajuny.resource

import no.sigurof.grajuny.resource.mesh.Mesh
import org.lwjgl.opengl.GL30

class MeshResource(
    private val mesh: Mesh,
    val attributes: List<Int>
) {

    fun render() {
        GL30.glDrawElements(GL30.GL_TRIANGLES, mesh.vertexCount, GL30.GL_UNSIGNED_INT, 0)
    }

    val vao: Int
        get() = mesh.vao

    fun activate() {
        GL30.glBindVertexArray(vao)
        for (attr in attributes) {
            GL30.glEnableVertexAttribArray(attr)
        }
    }

    fun deactivate() {
        for (attr in attributes) {
            GL30.glDisableVertexAttribArray(attr)
        }
        GL30.glBindVertexArray(0)
    }

}