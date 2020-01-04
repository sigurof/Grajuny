package no.sigurof.tutorial.lwjgl.resource

import no.sigurof.tutorial.lwjgl.resource.mesh.Mesh
import org.lwjgl.opengl.GL30

class TexturedMeshResource(
    private val tex: Int,
    mesh: Mesh
) : ResourceGl {
    private val meshResource: MeshResource = MeshResource(mesh)
    override fun render() {
        meshResource.render()
    }

    override fun prepare() {
        GL30.glActiveTexture(GL30.GL_TEXTURE0)
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, tex)
        meshResource.prepare()
    }

    override fun getVao(): Int {
        return meshResource.getVao()
    }
}