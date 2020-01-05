package no.sigurof.tutorial.lwjgl.entity.obj

import no.sigurof.tutorial.lwjgl.entity.surface.DiffuseSpecularSurface
import no.sigurof.tutorial.lwjgl.shaders.settings.impl.BillboardShaderSettings
import org.joml.Vector2f
import org.joml.Vector3f

class TexturedBbdSphereObject constructor(

    private val surface: DiffuseSpecularSurface,
    var position: Vector3f,
    private val polarAngles: Vector2f,
    private val radius: Float
) : GameObject<BillboardShaderSettings> {

    override fun loadUniforms(shader: BillboardShaderSettings) {
        shader.loadSphereCenter(position)
        shader.loadSphereRadius(radius)
        surface.loadUniforms(shader)
        shader.loadPolarAngles(polarAngles)
    }

}
