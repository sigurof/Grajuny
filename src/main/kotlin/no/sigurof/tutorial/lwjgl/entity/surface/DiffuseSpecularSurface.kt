package no.sigurof.tutorial.lwjgl.entity.surface

import no.sigurof.tutorial.lwjgl.shaders.settings.DefaultShaderSettings
import org.joml.Vector3f

// TODO Implement "DiffuseSurface" and "SpecularSurface"?
class DiffuseSpecularSurface constructor(
    private val shineDamper: Float,
    private val reflectivity: Float,
    private val color: Vector3f
) : Surface<DefaultShaderSettings> {

    override fun loadUniforms(shader: DefaultShaderSettings) {
        shader.loadSpecularValues(shineDamper, reflectivity)
        shader.loadColor(color)
    }
}