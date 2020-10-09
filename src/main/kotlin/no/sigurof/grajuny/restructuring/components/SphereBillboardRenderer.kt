package no.sigurof.grajuny.restructuring.components

import no.sigurof.grajuny.restructuring.TMaterial
import no.sigurof.grajuny.restructuring.TTexture
import no.sigurof.grajuny.restructuring.manager.TBillboardManager
import no.sigurof.grajuny.restructuring.node.GameComponent
import no.sigurof.grajuny.restructuring.resource.TBillboardResource
import no.sigurof.grajuny.restructuring.shader.Shader
import no.sigurof.grajuny.restructuring.shader.SphereBillboardShader
import no.sigurof.grajuny.restructuring.shader.interfaces.BillboardShader
import no.sigurof.grajuny.restructuring.shader.interfaces.ColorSpecularShader
import no.sigurof.grajuny.restructuring.shader.interfaces.TextureShader
import org.joml.Matrix4f
import org.joml.Vector3f

class SphereBillboardRenderer(
    val texture: TTexture,
    val material: TMaterial,
    val radius: Float,
    val position: Vector3f,
    shadersToUse: List<Shader> = listOf(SphereBillboardShader)
) : GameComponent(shadersToUse = shadersToUse) {

    private val billboard: TBillboardResource = TBillboardManager.getBillboardResource()

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