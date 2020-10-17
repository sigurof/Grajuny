package no.sigurof.grajuny.shader.shaders

import no.sigurof.grajuny.shader.Shader
import no.sigurof.grajuny.shader.ShaderManager
import no.sigurof.grajuny.shader.interfaces.CameraShader
import no.sigurof.grajuny.shader.interfaces.ColorShader
import no.sigurof.grajuny.shader.interfaces.ProjectionMatrixShader
import no.sigurof.grajuny.shader.interfaces.Shader3D
import org.joml.Matrix4f
import org.joml.Vector3f

object LineShader : Shader(
    vtxSource = "/shader/line/vertex.shader",
    frgSource = "/shader/line/fragment.shader",
    attributes = listOf(
        0 to "position"
    ),
    uniforms = listOf(
        "prjMatrix",
        "viewMatrix",
        "trMatrix",
        "color"
    )
),
    ProjectionMatrixShader,
    CameraShader,
    ColorShader,
    Shader3D {

    override fun loadProjectionMatrix(projectionMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locations.getValue("prjMatrix"), projectionMatrix)
    }

    override fun loadCameraPosition(cameraPosition: Vector3f) {

    }

    override fun loadViewMatrix(viewMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locations.getValue("viewMatrix"), viewMatrix)
    }

    override fun loadColor(color: Vector3f) {
        ShaderManager.loadVector3(locations.getValue("color"), color)
    }

    override fun loadTransformationMatrix(transformationMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locations.getValue("trMatrix"), transformationMatrix)
    }

}