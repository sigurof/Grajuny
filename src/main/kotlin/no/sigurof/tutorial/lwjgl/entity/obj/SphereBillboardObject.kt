package no.sigurof.tutorial.lwjgl.entity.obj

import no.sigurof.tutorial.lwjgl.entity.surface.DiffuseSpecularSurface
import no.sigurof.tutorial.lwjgl.shaders.settings.ShaderSettings
import no.sigurof.tutorial.lwjgl.shaders.settings.impl.BillboardShaderSettings
import org.joml.Vector3f

class SphereBillboardObject constructor(
    private val surface: DiffuseSpecularSurface,
    var position: Vector3f,
    private val radius: Float
) : GameObject {

    override fun loadUniforms(shader: ShaderSettings) {
        val shader = shader as BillboardShaderSettings
        shader.loadSphereCenter(position)
        shader.loadSphereRadius(radius)
        surface.loadUniforms(shader)
    }

}