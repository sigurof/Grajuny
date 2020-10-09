package no.sigurof.grajuny.components

import no.sigurof.grajuny.node.GameComponent
import no.sigurof.grajuny.resource.Material
import no.sigurof.grajuny.resource.MeshResource
import no.sigurof.grajuny.resource.Texture
import no.sigurof.grajuny.shader.Basic3DShader
import no.sigurof.grajuny.shader.Shader
import no.sigurof.grajuny.shader.interfaces.ColorSpecularShader
import no.sigurof.grajuny.shader.interfaces.Shader3D
import no.sigurof.grajuny.shader.interfaces.TextureShader
import org.joml.Matrix4f

class MeshRenderer(
    val mesh: MeshResource,
    val texture: Texture? = null,
    val material: Material,
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