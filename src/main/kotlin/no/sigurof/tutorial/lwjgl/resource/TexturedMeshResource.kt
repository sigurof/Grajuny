package no.sigurof.tutorial.lwjgl.resource

import no.sigurof.tutorial.lwjgl.resource.mesh.Mesh
import org.lwjgl.opengl.GL30

class TexturedMeshResource(
    private val mesh: Mesh,
    private val tex: Int
) : ResourceGl {
    override fun render() {
        GL30.glDrawElements(GL30.GL_TRIANGLES, mesh.vertexCount, GL30.GL_UNSIGNED_INT, 0)
    }

    override fun prepare() {
        GL30.glActiveTexture(GL30.GL_TEXTURE0)
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, tex)
    }

    override fun getVao(): Int {
        return mesh.vao
    }
}