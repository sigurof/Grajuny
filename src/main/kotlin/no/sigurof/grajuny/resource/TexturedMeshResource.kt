package no.sigurof.grajuny.resource

import no.sigurof.grajuny.resource.mesh.Mesh
import no.sigurof.grajuny.shaders.settings.impl.StandardShader
import org.lwjgl.opengl.GL30

class TexturedMeshResource(
    private val tex: Int,
    mesh: Mesh
) : ResourceGl<StandardShader> {
    private val meshResource: MeshResource = MeshResource(mesh)
    override fun render() {
        meshResource.render()
    }

    override fun using(shader: StandardShader, function: () -> Unit) {
        GL30.glActiveTexture(GL30.GL_TEXTURE0)
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, tex)
        shader.loadUseTexture(true)
        function()
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0)
    }

    override val vao: Int
        get() = meshResource.vao

}