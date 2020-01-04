package no.sigurof.tutorial.lwjgl.entity.obj

import no.sigurof.tutorial.lwjgl.entity.surface.DiffuseSpecularSurface
import no.sigurof.tutorial.lwjgl.shaders.settings.DefaultShaderSettings
import no.sigurof.tutorial.lwjgl.shaders.settings.ShaderSettings
import no.sigurof.tutorial.lwjgl.utils.Maths
import org.joml.Vector3f

class PlainObject constructor(
    private val surface: DiffuseSpecularSurface,
    private var position: Vector3f,
    private val eulerAngles: Vector3f,
    private val scale: Vector3f
) : GameObject {

    override fun loadUniforms(shader: ShaderSettings) {
        val shader = shader as DefaultShaderSettings
        shader.loadTransformationMatrix(Maths.createTransformationMatrix(position, eulerAngles, scale))
        surface.loadUniforms(shader)
    }
}