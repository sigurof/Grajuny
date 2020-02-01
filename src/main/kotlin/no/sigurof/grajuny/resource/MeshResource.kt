package no.sigurof.grajuny.resource

import no.sigurof.grajuny.resource.mesh.Mesh
import no.sigurof.grajuny.shaders.settings.impl.StandardShader
import org.lwjgl.opengl.GL30

class MeshResource(
    private val mesh: Mesh,
    val attributes: List<Int>
) : ResourceGl<StandardShader> {

    override fun render() {
        GL30.glDrawElements(GL30.GL_TRIANGLES, mesh.vertexCount, GL30.GL_UNSIGNED_INT, 0)
    }

    override val vao: Int
        get() = mesh.vao

    override fun activate(shader: StandardShader) {
        GL30.glBindVertexArray(vao)
        for (attr in attributes) {
            GL30.glEnableVertexAttribArray(attr)
        }
        shader.loadUseTexture(false)
    }

    override fun deactivate(shader: StandardShader) {
        for (attr in attributes) {
            GL30.glDisableVertexAttribArray(attr)
        }
        GL30.glBindVertexArray(0)
    }

}