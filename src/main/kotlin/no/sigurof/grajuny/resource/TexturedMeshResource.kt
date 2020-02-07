package no.sigurof.grajuny.resource

import no.sigurof.grajuny.resource.mesh.Mesh
import org.lwjgl.opengl.GL30

class TexturedMeshResource(
    private val tex: Int,
    mesh: Mesh,
    attributes: List<Int>
) : ResourceGl {
    private val meshResource: MeshResource = MeshResource(mesh, attributes)

    override fun render() {
        meshResource.render()
    }

    override val vao: Int
        get() = meshResource.vao

    override fun activate() {
        GL30.glBindVertexArray(vao)
        for (attr in meshResource.attributes) {
            GL30.glEnableVertexAttribArray(attr)
        }
        GL30.glActiveTexture(GL30.GL_TEXTURE0)
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, tex)
    }

    override fun deactivate() {
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0)
        for (attr in meshResource.attributes) {
            GL30.glDisableVertexAttribArray(attr)
        }
        GL30.glBindVertexArray(0)
    }

}