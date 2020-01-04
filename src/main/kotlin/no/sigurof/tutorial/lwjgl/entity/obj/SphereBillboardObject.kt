package no.sigurof.tutorial.lwjgl.entity.obj

import no.sigurof.tutorial.lwjgl.entity.surface.DiffuseSpecularSurface
import no.sigurof.tutorial.lwjgl.shaders.settings.impl.BillboardShaderSettings
import org.joml.Vector3f

class SphereBillboardObject constructor(
    private val surface: DiffuseSpecularSurface,
    var position: Vector3f,
    val radius: Float
) : GameObject<BillboardShaderSettings> {

    override fun loadUniforms(shader: BillboardShaderSettings) {
        shader.loadSphereCenter(position)
        shader.loadSphereRadius(radius)
        surface.loadUniforms(shader)
    }

}