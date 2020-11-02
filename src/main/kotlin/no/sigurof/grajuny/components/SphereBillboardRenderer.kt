package no.sigurof.grajuny.components

import no.sigurof.grajuny.manager.BillboardManager
import no.sigurof.grajuny.node.GameComponent
import no.sigurof.grajuny.node.GameObject
import no.sigurof.grajuny.rendable.Rendable
import no.sigurof.grajuny.resource.BillboardResource
import no.sigurof.grajuny.resource.material.Material
import no.sigurof.grajuny.shader.Shader
import no.sigurof.grajuny.shader.shaders.phong.PhongBillboardShader
import org.joml.Matrix4f
import org.joml.Vector3f

class SphereBillboardRenderer(
    val material: Material,
    val radius: Float,
    val position: Vector3f,// TODO DElete this property,
    shadersToUse: List<Shader> = listOf(PhongBillboardShader)
) : GameComponent, Rendable(
    shadersToUse = shadersToUse
) {

    private val billboard: BillboardResource = BillboardManager.getBillboardResource()
    override var parent: GameObject? = null

    override fun input() {
    }

    override fun onRender(shader: Shader, transform: Matrix4f) {
        if (shader is PhongBillboardShader){
            material.activate()
            material.render(shader)
            shader.loadSphereCenter(transform.getColumn(3, Vector3f()))
            shader.loadSphereRadius(radius)
            billboard.activate()
            billboard.render()
            billboard.deactivate()
            material.deactivate()
        }
    }

    override fun update() {
    }

}