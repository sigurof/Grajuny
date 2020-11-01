package no.sigurof.grajuny.resource.material

import no.sigurof.grajuny.shader.Shader
import no.sigurof.grajuny.shader.shaders.PhongMeshShader
import no.sigurof.grajuny.shader.shaders.SphereBillboardShader
import org.joml.Vector3f

class RegularMaterial(
    var color: Vector3f,
    val reflectivity: Float = 10f,
    val shineDamper: Float = 10f,
    val specular: Boolean = true,
    val diffuse: Boolean = true
) : Material {

    override fun render(shader: Shader) {
        if (shader is PhongMeshShader) {
            shader.loadColor(color)
            shader.loadSpecularValues(shineDamper, reflectivity)
            shader.loadUseDiffuse(diffuse)
            shader.loadUseSpecular(specular)
        }
        if (shader is SphereBillboardShader) {
            shader.loadColor(color)
            shader.loadSpecularValues(shineDamper, reflectivity)
            shader.loadUseDiffuse(diffuse)
            shader.loadUseSpecular(specular)
        }
    }

    override fun activate() {
    }

    override fun deactivate() {
    }

}