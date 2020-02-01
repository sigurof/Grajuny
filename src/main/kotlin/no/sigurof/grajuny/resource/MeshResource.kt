package no.sigurof.grajuny.resource

import no.sigurof.grajuny.resource.mesh.Mesh
import no.sigurof.grajuny.shaders.settings.impl.StandardShader
import org.lwjgl.opengl.GL30

class MeshResource(
    private val mesh: Mesh
) : ResourceGl<StandardShader> {
    override fun render() {
        GL30.glDrawElements(GL30.GL_TRIANGLES, mesh.vertexCount, GL30.GL_UNSIGNED_INT, 0)
    }

    override fun using(shader: StandardShader, function: () -> Unit) {
        shader.loadUseTexture(false)
        function()
    }

    override val vao: Int
        get() = mesh.vao

}