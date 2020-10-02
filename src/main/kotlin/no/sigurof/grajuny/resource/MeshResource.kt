package no.sigurof.grajuny.resource

import no.sigurof.grajuny.resource.mesh.Mesh
import no.sigurof.grajuny.shaders.settings.impl.PlainShaderSettings
import org.lwjgl.opengl.GL30

class MeshResource(
    private val mesh: Mesh
) : ResourceGl<PlainShaderSettings> {
    override fun render() {
        GL30.glDrawElements(GL30.GL_TRIANGLES, mesh.vertexCount, GL30.GL_UNSIGNED_INT, 0)
    }

    override fun using(shader: PlainShaderSettings, function: () -> Unit) {
        shader.loadUseTexture(false)
        function()
    }

    override fun getVao(): Int {
        return mesh.vao
    }
}