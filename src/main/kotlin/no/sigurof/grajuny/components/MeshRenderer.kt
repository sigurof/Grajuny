package no.sigurof.grajuny.components

import no.sigurof.grajuny.node.GameComponent
import no.sigurof.grajuny.node.GameObject
import no.sigurof.grajuny.rendable.Rendable
import no.sigurof.grajuny.resource.MeshResource
import no.sigurof.grajuny.resource.material.Material
import no.sigurof.grajuny.resource.mesh.MeshManager
import no.sigurof.grajuny.resource.texture.TextureRenderer
import no.sigurof.grajuny.shader.Shader
import no.sigurof.grajuny.shader.shaders.PhongMeshShader
import no.sigurof.grajuny.shader.shaders.PhongMeshShader2

import org.joml.Matrix4f

class MeshRenderer(
    val textureRenderer: TextureRenderer? = null,
    val material: Material,
    meshName: String,
    shadersToUse: List<Shader> = listOf(PhongMeshShader)
) : GameComponent, Rendable(
    shadersToUse = shadersToUse
) {
    val mesh: MeshResource = MeshManager.getMeshResource(meshName)
    override var parent: GameObject? = null

    override fun input() {

    }

    override fun onRender(shader: Shader, transform: Matrix4f) {
        if (shader is PhongMeshShader) {
            material.render(shader)
            textureRenderer?.activate()
            textureRenderer?.render(shader) ?: shader.loadUseTexture(false)
            shader.loadTransformationMatrix(transform)
            mesh.activate()
            mesh.render()
            mesh.deactivate()
            textureRenderer?.deactivate()
        }
        if (shader is PhongMeshShader2) {
            material.render(shader)
            textureRenderer?.activate()
            textureRenderer?.render(shader) ?: shader.loadUseTexture(false)
            shader.loadTransformationMatrix(transform)
            mesh.activate()
            mesh.render()
            mesh.deactivate()
            textureRenderer?.deactivate()
        }
    }

    override fun update() {

    }
}