package no.sigurof.grajuny.light.phong

import no.sigurof.grajuny.light.Light
import no.sigurof.grajuny.shader.Shader
import no.sigurof.grajuny.shader.shaders.phong.PhongBillboardShader
import no.sigurof.grajuny.shader.shaders.phong.PhongMeshShader
import org.joml.Vector3f

data class PointLight(
    val position: Vector3f,
    val constant: Float,
    val linear: Float,
    val quadratic: Float,
    val ambient: Vector3f,
    val diffuse: Vector3f,
    val specular: Vector3f
) : Light {

    override fun render(shader: Shader) {
        if (shader is PhongMeshShader) {
            shader.loadLight(this)
        }
        if (shader is PhongBillboardShader) {
            shader.loadLight(this)
        }
    }

}