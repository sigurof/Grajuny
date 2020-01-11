package no.sigurof.grajuny.entity.obj

import no.sigurof.grajuny.entity.surface.DiffuseSpecularSurface
import no.sigurof.grajuny.shaders.settings.DefaultShaderSettings
import no.sigurof.grajuny.utils.Maths
import org.joml.Vector3f

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
}