package no.sigurof.grajuny.components

import no.sigurof.grajuny.node.GameComponent
import no.sigurof.grajuny.resource.MeshResource
import no.sigurof.grajuny.resource.Texture
import no.sigurof.grajuny.resource.material.Material
import no.sigurof.grajuny.shader.Shader
import no.sigurof.grajuny.shader.shaders.Basic3DShader
import no.sigurof.grajuny.shader.shaders.FeaturelessShader
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
        if (shader in shadersToUse) {
            if (shader is Basic3DShader) {
                material.render(shader)
                texture?.activate()
                texture?.render(shader) ?: shader.loadUseTexture(false)
                shader.loadTransformationMatrix(transform)
                mesh.activate()
                mesh.render()
                mesh.deactivate()
                texture?.deactivate()
            }
            if (shader is FeaturelessShader) {
                material.render(shader)
                shader.loadTransformationMatrix(transform)
                mesh.activate()
                mesh.render()
                mesh.deactivate()
            }
        }
    }

    override fun update() {

    }
}