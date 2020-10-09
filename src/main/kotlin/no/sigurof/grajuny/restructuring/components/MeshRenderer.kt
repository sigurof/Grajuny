package no.sigurof.grajuny.restructuring.components

import no.sigurof.grajuny.restructuring.TMaterial
import no.sigurof.grajuny.restructuring.TMeshResource
import no.sigurof.grajuny.restructuring.TTexture
import no.sigurof.grajuny.restructuring.node.GameComponent
import no.sigurof.grajuny.restructuring.shader.Basic3DShader
import no.sigurof.grajuny.restructuring.shader.Shader
import no.sigurof.grajuny.restructuring.shader.interfaces.ColorSpecularShader
import no.sigurof.grajuny.restructuring.shader.interfaces.Shader3D
import no.sigurof.grajuny.restructuring.shader.interfaces.TextureShader
import org.joml.Matrix4f

class MeshRenderer(
    val mesh: TMeshResource,
    val texture: TTexture? = null,
    val material: TMaterial,
    shadersToUse: List<Shader> = listOf(Basic3DShader)
) : GameComponent(
    shadersToUse = shadersToUse
) {
    override var transform: Matrix4f = Matrix4f()

    override fun input() {

    }

    override fun upload(shader: Shader, transform: Matrix4f) {
        if (shader is ColorSpecularShader
            && shader is TextureShader
            && shader is Shader3D
        ) {
            shader.loadTransformationMatrix(transform)
            material.render(shader)
            texture?.activate()
            texture?.render(shader) ?: shader.loadUseTexture(false)
            mesh.activate()
            mesh.render()
            mesh.deactivate()
            texture?.deactivate()
        }
    }

    override fun update() {

    }
}