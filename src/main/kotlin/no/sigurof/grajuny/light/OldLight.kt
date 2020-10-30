package no.sigurof.grajuny.light

import no.sigurof.grajuny.shader.Shader
import no.sigurof.grajuny.shader.shaders.PhongMeshShader
import org.joml.Vector3f

class OldLight(
    var position: Vector3f,
    var color: Vector3f,
    var ambient: Float
) : Light {

    override fun render(shader: Shader) {
        if (shader is PhongMeshShader) {
            shader.loadLight(this)
        }
    }

}