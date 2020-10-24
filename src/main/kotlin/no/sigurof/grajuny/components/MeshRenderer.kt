package no.sigurof.grajuny.components

import no.sigurof.grajuny.node.GameComponent
import no.sigurof.grajuny.node.GameObject
import no.sigurof.grajuny.rendable.Rendable
import no.sigurof.grajuny.resource.MeshResource
import no.sigurof.grajuny.resource.material.Material
import no.sigurof.grajuny.resource.mesh.MeshManager
import no.sigurof.grajuny.resource.texture.Texture
import no.sigurof.grajuny.shader.Shader
import no.sigurof.grajuny.shader.shaders.Basic3DShader

import org.joml.Matrix4f

class MeshRenderer(
    val texture: Texture? = null,
    val material: Material,
    meshName: String
) : GameComponent, Rendable(
    shadersToUse = listOf(Basic3DShader)
) {
    val mesh: MeshResource = MeshManager.getMeshResource(meshName)
    override var parent: GameObject? = null

    override fun input() {

    }

    override fun onRender(shader: Shader, transform: Matrix4f) {
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
    }

    override fun update() {

    }
}