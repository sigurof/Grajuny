package no.sigurof.grajuny.restructuring.shader

import no.sigurof.grajuny.restructuring.LightSource
import no.sigurof.grajuny.restructuring.shader.interfaces.CameraShader
import no.sigurof.grajuny.restructuring.shader.interfaces.ColorSpecularShader
import no.sigurof.grajuny.restructuring.shader.interfaces.LightShader
import no.sigurof.grajuny.restructuring.shader.interfaces.ProjectionMatrixShader
import no.sigurof.grajuny.restructuring.shader.interfaces.Shader3D
import no.sigurof.grajuny.restructuring.shader.interfaces.TextureShader
import no.sigurof.grajuny.shaders.ShaderManager
import org.joml.Matrix4f
import org.joml.Vector3f

private const val vtxSource = "/shader/texture/vertex.shader"
private const val frgSource = "/shader/texture/fragment.shader"

object Basic3DShader : Shader(),
    TextureShader,
    ColorSpecularShader,
    Shader3D,
    ProjectionMatrixShader,
    CameraShader,
    LightShader{
    val attributes = listOf(
        0 to "position",
        1 to "textureCoords",
        2 to "normal"
    )
    private val uniforms = listOf(
        "trMatrix",
        "prjMatrix",
        "viewMatrix",
        "lightPos",
        "cameraPos",

        "lightCol",
        "shineDamper",
        "reflectivity",
        "ambient",
        "color",
        "useTexture"
    )
    override val program: Int = ShaderManager.compileProgram(vtxSource, frgSource, attributes)
    override val locations = uniforms.map { it to ShaderManager.getUniformLocation(it, program) }.toMap()

    override fun loadTransformationMatrix(transformationMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locations.getValue("trMatrix"), transformationMatrix)
    }

    override fun loadProjectionMatrix(projectionMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locations.getValue("prjMatrix"), projectionMatrix)
    }

    override fun loadViewMatrix(viewMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locations.getValue("viewMatrix"), viewMatrix)
    }

    override fun loadSpecularValues(damper: Float, reflectivity: Float) {
        ShaderManager.loadFloat(locations.getValue("shineDamper"), damper)
        ShaderManager.loadFloat(locations.getValue("reflectivity"), reflectivity)
    }

    override fun loadCameraPosition(cameraPosition: Vector3f) {
        ShaderManager.loadVector3(locations.getValue("cameraPos"), cameraPosition)
    }

    override fun loadColor(color: Vector3f) {
        ShaderManager.loadVector3(locations.getValue("color"), color);
    }

    override fun loadLight(lightSource: LightSource) {
        ShaderManager.loadVector3(locations.getValue("lightPos"), lightSource.position)
        ShaderManager.loadVector3(locations.getValue("lightCol"), lightSource.color)
        ShaderManager.loadFloat(locations.getValue("ambient"), lightSource.ambient)
    }

    override fun loadUseTexture(useTexture: Boolean) {
        ShaderManager.loadBoolean(locations.getValue("useTexture"), useTexture)
    }

}