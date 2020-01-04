package no.sigurof.tutorial.lwjgl.resource

import no.sigurof.tutorial.lwjgl.resource.mesh.Mesh
import org.lwjgl.opengl.GL30

class MeshResource(
    private val mesh: Mesh
) : ResourceGl {
    override fun render() {
        GL30.glDrawElements(GL30.GL_TRIANGLES, mesh.vertexCount, GL30.GL_UNSIGNED_INT, 0)
    }

    override fun prepare() {
    }

    override fun getVao(): Int {
        return mesh.vao
    }
}