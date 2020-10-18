package no.sigurof.grajuny.resource.material

import no.sigurof.grajuny.shader.Shader
import no.sigurof.grajuny.shader.shaders.Basic3DShader
import no.sigurof.grajuny.shader.shaders.SphereBillboardShader
import org.joml.Vector3f

class RegularMaterial(
    var color: Vector3f,
    val reflectivity: Float = 1f,
    val shineDamper: Float = 1f,
    val specular: Boolean = true,
    val diffuse: Boolean = true
) : Material {

    override fun render(shader: Shader) {
        if (shader is Basic3DShader) {
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

}