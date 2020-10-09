package no.sigurof.grajuny.restructuring.shader

import no.sigurof.grajuny.restructuring.LightSource
import no.sigurof.grajuny.restructuring.shader.interfaces.BillboardShader
import no.sigurof.grajuny.restructuring.shader.interfaces.CameraShader
import no.sigurof.grajuny.restructuring.shader.interfaces.ColorSpecularShader
import no.sigurof.grajuny.restructuring.shader.interfaces.LightShader
import no.sigurof.grajuny.restructuring.shader.interfaces.ProjectionMatrixShader
import no.sigurof.grajuny.restructuring.shader.interfaces.TextureShader
import no.sigurof.grajuny.shaders.ShaderManager
import org.joml.Matrix4f
import org.joml.Vector3f

private const val vtxSource = "/shader/billboard/vertex.shader"
private const val frgSource = "/shader/billboard/fragment.shader"

object SphereBillboardShader : Shader(),
    TextureShader,
    ColorSpecularShader,
    ProjectionMatrixShader,
    CameraShader,
    LightShader,
    BillboardShader {
    val attributes: List<Pair<Int, String>> = emptyList()

    private val uniforms = listOf(
        "prjMatrix",
        "viewMatrix",
        "sphereRadius",
        "sphereCenter",
        "cameraPos",
        "lightPos",
        "lightCol",
        "ambient",
        "color",
        "shineDamper",
        "reflectivity",

        "frUseTexture",
        "frPrjMatrix",
        "frViewMatrix",
        "frSphereRadius",
        "frCameraPos",
        "frSphereCenter"
    )

    override val program: Int = ShaderManager.compileProgram(vtxSource, frgSource, attributes)
    override val locations = uniforms.map { it to ShaderManager.getUniformLocation(it, program) }.toMap()

    override fun loadUseTexture(useTexture: Boolean) {
        ShaderManager.loadBoolean(locations.getValue("frUseTexture"), useTexture)
    }

    override fun loadColor(color: Vector3f) {
        ShaderManager.loadVector3(locations.getValue("color"), color)
    }

    override fun loadSpecularValues(damper: Float, reflectivity: Float) {
        ShaderManager.loadFloat(locations.getValue("shineDamper"), damper)
        ShaderManager.loadFloat(locations.getValue("reflectivity"), reflectivity)
    }

    override fun loadProjectionMatrix(projectionMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locations.getValue("prjMatrix"), projectionMatrix)
        ShaderManager.loadMatrix(locations.getValue("frPrjMatrix"), projectionMatrix)
    }

    override fun loadViewMatrix(viewMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locations.getValue("viewMatrix"), viewMatrix)
        ShaderManager.loadMatrix(locations.getValue("frViewMatrix"), viewMatrix)
    }

    override fun loadCameraPosition(cameraPosition: Vector3f) {
        ShaderManager.loadVector3(locations.getValue("cameraPos"), cameraPosition)
        ShaderManager.loadVector3(locations.getValue("frCameraPos"), cameraPosition)
    }

    override fun loadLight(lightSource: LightSource) {
        ShaderManager.loadVector3(locations.getValue("lightPos"), lightSource.position)
        ShaderManager.loadVector3(locations.getValue("lightCol"), lightSource.color)
        ShaderManager.loadFloat(locations.getValue("ambient"), lightSource.ambient)
    }


    override fun loadSphereCenter(sphereCenter: Vector3f) {
        ShaderManager.loadVector3(locations.getValue("sphereCenter"), sphereCenter)
        ShaderManager.loadVector3(locations.getValue("frSphereCenter"), sphereCenter)
    }

    override fun loadSphereRadius(radius: Float) {
        ShaderManager.loadFloat(locations.getValue("sphereRadius"), radius)
        ShaderManager.loadFloat(locations.getValue("frSphereRadius"), radius)
    }
}