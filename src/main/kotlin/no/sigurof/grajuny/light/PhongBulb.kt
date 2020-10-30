package no.sigurof.grajuny.light

import no.sigurof.grajuny.shader.Shader
import no.sigurof.grajuny.shader.shaders.PhongMeshShader
import no.sigurof.grajuny.shader.shaders.PhongMeshShader2
import no.sigurof.grajuny.shader.shaders.SphereBillboardShader
import org.joml.Vector3f

class PhongBulb(
    val position: Vector3f,
    val ambient: Vector3f,
    val diffuse: Vector3f,
    val specular: Vector3f
) : Bulb {

    override fun render(shader: Shader) {
        if (shader is SphereBillboardShader) {
            shader.loadLight(
                LightBulb(
                    position = position,
                    color = diffuse,
                    ambient = ambient.length()
                )
            )
        }
        if (shader is PhongMeshShader) {
            shader.loadLight(
                LightBulb(
                    position = position,
                    color = diffuse,
                    ambient = ambient.length()
                )
            )
        }
        if (shader is PhongMeshShader2) {
            shader.loadLight(this)
        }
    }

}