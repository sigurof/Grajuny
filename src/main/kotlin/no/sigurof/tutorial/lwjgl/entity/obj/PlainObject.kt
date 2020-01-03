package no.sigurof.tutorial.lwjgl.entity.obj

import no.sigurof.tutorial.lwjgl.entity.surface.DiffuseSpecularSurface
import no.sigurof.tutorial.lwjgl.mesh.Vao
import no.sigurof.tutorial.lwjgl.shaders.settings.DefaultShaderSettings
import no.sigurof.tutorial.lwjgl.utils.Maths
import org.joml.Vector3f
import org.lwjgl.opengl.GL30

class PlainObject constructor(
    private val surface: DiffuseSpecularSurface,
    private var position: Vector3f,
    private val eulerAngles: Vector3f,
    private val scale: Vector3f
) : GameObject<DefaultShaderSettings> {

    override fun loadUniforms(shader: DefaultShaderSettings) {
        shader.loadTransformationMatrix(Maths.createTransformationMatrix(position, eulerAngles, scale))
        surface.loadUniforms(shader)
    }
    override fun render(vao: Vao) {
        GL30.glDrawElements(GL30.GL_TRIANGLES, vao.vertexCount, GL30.GL_UNSIGNED_INT, 0)
    }
}