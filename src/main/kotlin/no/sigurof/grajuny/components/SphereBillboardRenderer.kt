package no.sigurof.grajuny.components

import no.sigurof.grajuny.manager.BillboardManager
import no.sigurof.grajuny.node.GameComponent
import no.sigurof.grajuny.node.GameObject
import no.sigurof.grajuny.rendable.Rendable
import no.sigurof.grajuny.resource.BillboardResource
import no.sigurof.grajuny.resource.material.Material
import no.sigurof.grajuny.resource.texture.Texture
import no.sigurof.grajuny.shader.Shader
import no.sigurof.grajuny.shader.shaders.SphereBillboardShader
import org.joml.Matrix4f
import org.joml.Vector3f

class SphereBillboardRenderer(
    val texture: Texture? = null,
    val material: Material,
    val radius: Float,
    val position: Vector3f
) : GameComponent, Rendable(
    shadersToUse = listOf(SphereBillboardShader)
) {

    private val billboard: BillboardResource = BillboardManager.getBillboardResource()
    override var parent: GameObject? = null

    override fun input() {
    }

    override fun onRender(shader: Shader, transform: Matrix4f) {
        if (shader is SphereBillboardShader) {
            material.render(shader)
            shader.loadSphereCenter(transform.getColumn(3, Vector3f()))
            shader.loadSphereRadius(radius)
            texture?.render(shader) ?: shader.loadUseTexture(false)
            texture?.activate()
            texture?.render(shader)
            billboard.activate()
            billboard.render()
            billboard.deactivate()
            texture?.deactivate()
        }
    }

    override fun update() {
    }

}