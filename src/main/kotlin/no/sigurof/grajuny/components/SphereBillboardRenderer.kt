package no.sigurof.grajuny.components

import no.sigurof.grajuny.manager.BillboardManager
import no.sigurof.grajuny.node.GameComponent
import no.sigurof.grajuny.resource.BillboardResource
import no.sigurof.grajuny.resource.Material
import no.sigurof.grajuny.resource.Texture
import no.sigurof.grajuny.shader.Shader
import no.sigurof.grajuny.shader.SphereBillboardShader
import no.sigurof.grajuny.shader.interfaces.BillboardShader
import no.sigurof.grajuny.shader.interfaces.ColorSpecularShader
import no.sigurof.grajuny.shader.interfaces.TextureShader
import org.joml.Matrix4f
import org.joml.Vector3f

class SphereBillboardRenderer(
    val texture: Texture,
    val material: Material,
    val radius: Float,
    val position: Vector3f,
    shadersToUse: List<Shader> = listOf(SphereBillboardShader)
) : GameComponent(shadersToUse = shadersToUse) {

    private val billboard: BillboardResource = BillboardManager.getBillboardResource()

    override var transform: Matrix4f = Matrix4f()

    override fun input() {
    }

    override fun upload(shader: Shader, transform: Matrix4f) {
        if (
            shader is ColorSpecularShader
            && shader is TextureShader
            && shader is BillboardShader
        ) {
            shader.loadSphereCenter(position)
            shader.loadSphereRadius(radius)
            material.render(shader)
            texture.activate()
            texture.render(shader)
            billboard.activate()
            billboard.render()
            billboard.deactivate()
            texture.deactivate()
        }
    }

    override fun update() {
    }

}