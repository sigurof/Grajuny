package no.sigurof.grajuny.shader.shaders

import no.sigurof.grajuny.light.LightSource
import no.sigurof.grajuny.shader.Shader
import no.sigurof.grajuny.shader.ShaderManager
import no.sigurof.grajuny.shader.interfaces.BillboardShader
import no.sigurof.grajuny.shader.interfaces.CameraShader
import no.sigurof.grajuny.shader.interfaces.ColorShader
import no.sigurof.grajuny.shader.interfaces.LightShader
import no.sigurof.grajuny.shader.interfaces.ProjectionMatrixShader
import no.sigurof.grajuny.shader.interfaces.SpecularShader
import no.sigurof.grajuny.shader.interfaces.TextureShader
import org.joml.Matrix4f
import org.joml.Vector3f

object SphereBillboardShader : Shader(
    vtxSource = "/shader/billboard/vertex.shader",
    frgSource = "/shader/billboard/fragment.shader",
    attributes = emptyList(),
    uniforms = listOf(
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
        "frSphereCenter",
        "useSpecular",
        "useDiffuse"
    )
),
    TextureShader,
    ColorShader,
    SpecularShader,
    ProjectionMatrixShader,
    CameraShader,
    LightShader,
    BillboardShader {

    override fun loadUseTexture(useTexture: Boolean) {
        ShaderManager.loadBoolean(locations.getValue("frUseTexture"), useTexture)
    }

    override fun loadColor(color: Vector3f) {
        ShaderManager.loadVector3(locations.getValue("color"), color)
    }

    override fun loadSpecularValues(damper: Float, reflectivity: Float) {
        ShaderManager.loadBoolean(locations.getValue("useSpecular"), true)
        ShaderManager.loadBoolean(locations.getValue("useDiffuse"), true)
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

    fun loadUseSpecular(useSpecular: Boolean) {
        ShaderManager.loadBoolean(locations.getValue("useSpecular"), useSpecular)
    }

    fun loadUseDiffuse(useDiffuse: Boolean) {
        ShaderManager.loadBoolean(locations.getValue("useDiffuse"), useDiffuse)
    }
}