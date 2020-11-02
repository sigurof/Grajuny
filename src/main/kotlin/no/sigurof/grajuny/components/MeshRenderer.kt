package no.sigurof.grajuny.components

import no.sigurof.grajuny.node.GameComponent
import no.sigurof.grajuny.node.GameObject
import no.sigurof.grajuny.rendable.Rendable
import no.sigurof.grajuny.resource.MeshResource
import no.sigurof.grajuny.resource.material.Material
import no.sigurof.grajuny.resource.mesh.MeshManager
import no.sigurof.grajuny.shader.Shader
import no.sigurof.grajuny.shader.shaders.phong.PhongMeshShader

import org.joml.Matrix4f

class MeshRenderer(
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
            material.activate()
            material.render(shader)
            shader.loadTransformationMatrix(transform)
            mesh.activate()
            mesh.render()
            mesh.deactivate()
            material.deactivate()
        }
    }

    override fun update() {

    }
}