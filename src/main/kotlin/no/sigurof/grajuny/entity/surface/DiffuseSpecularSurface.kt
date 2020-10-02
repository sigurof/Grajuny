package no.sigurof.grajuny.entity.surface

import no.sigurof.grajuny.shaders.settings.DefaultShaderSettings
import org.joml.Vector3f

// TODO Implement "DiffuseSurface" and "SpecularSurface"?
class DiffuseSpecularSurface constructor(
    var shineDamper: Float,
    var reflectivity: Float,
    var color: Vector3f
) : Surface<DefaultShaderSettings> {

    override fun loadUniforms(shader: DefaultShaderSettings) {
        shader.loadSpecularValues(shineDamper, reflectivity)
        shader.loadColor(color)
    }
}