package no.sigurof.grajuny.resource.material

import no.sigurof.grajuny.shader.Shader
import no.sigurof.grajuny.shader.shaders.Basic3DShader
import no.sigurof.grajuny.shader.shaders.SilhouetteShader
import no.sigurof.grajuny.shader.shaders.SphereBillboardShader
import org.joml.Vector3f

class ReflectiveMaterial(
    val color: Vector3f,
    val reflectivity: Float,
    val shineDamper: Float
) : Material {

    override fun render(shader: Shader) {
        if (shader is Basic3DShader) {
            shader.loadColor(color)
            shader.loadSpecularValues(shineDamper, reflectivity)

        }
        if (shader is SphereBillboardShader) {
            shader.loadColor(color)
            shader.loadSpecularValues(shineDamper, reflectivity)

        }
        if (shader is SilhouetteShader){
            shader.loadColor(color)
        }
    }

}