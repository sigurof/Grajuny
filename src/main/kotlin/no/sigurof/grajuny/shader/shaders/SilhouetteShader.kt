package no.sigurof.grajuny.shader.shaders

import no.sigurof.grajuny.shader.Shader
import no.sigurof.grajuny.shader.ShaderManager
import no.sigurof.grajuny.shader.interfaces.CameraShader
import no.sigurof.grajuny.shader.interfaces.ColorShader
import no.sigurof.grajuny.shader.interfaces.ProjectionMatrixShader
import no.sigurof.grajuny.shader.interfaces.Shader3D
import org.joml.Matrix4f
import org.joml.Vector3f

object SilhouetteShader : Shader(
    vtxSource = "/shader/silhouette/vertex.shader",
    frgSource = "/shader/silhouette/fragment.shader",
    attributes = listOf(
        0 to "position"
    ),
    uniforms = listOf(
        "trMatrix",
        "prjMatrix",
        "viewMatrix",
        "color"
    )
),
    Shader3D,
    CameraShader,
    ProjectionMatrixShader,
    ColorShader {

    override fun loadTransformationMatrix(transformationMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locations.getValue("trMatrix"), transformationMatrix)
    }

    override fun loadColor(color: Vector3f) {
        ShaderManager.loadVector3(locations.getValue("color"), color);
    }

    override fun loadCameraPosition(cameraPosition: Vector3f) {
        // Not needed
    }

    override fun loadViewMatrix(viewMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locations.getValue("viewMatrix"), viewMatrix)
    }

    override fun loadProjectionMatrix(projectionMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locations.getValue("prjMatrix"), projectionMatrix)
    }
}