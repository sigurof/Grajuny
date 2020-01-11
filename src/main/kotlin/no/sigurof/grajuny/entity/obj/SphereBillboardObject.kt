package no.sigurof.grajuny.entity.obj

import no.sigurof.grajuny.entity.surface.DiffuseSpecularSurface
import no.sigurof.grajuny.shaders.settings.impl.BillboardShaderSettings
import org.joml.Vector3f

class SphereBillboardObject constructor(
    private val surface: DiffuseSpecularSurface,
    var position: Vector3f,
    private val radius: Float
) : GameObject<BillboardShaderSettings> {

    override fun loadUniforms(shader: BillboardShaderSettings) {
        shader.loadSphereCenter(position)
        shader.loadSphereRadius(radius)
        surface.loadUniforms(shader)
    }

}