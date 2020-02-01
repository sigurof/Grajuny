package no.sigurof.grajuny.shaders.settings.impl

import no.sigurof.grajuny.entity.Light
import no.sigurof.grajuny.shaders.ShaderManager
import no.sigurof.grajuny.shaders.settings.DefaultShaderSettings
import org.joml.Matrix4f
import org.joml.Vector3f

private const val vtxSource = "/shader/billboard/vertex.shader"
private const val frgSource = "/shader/billboard/fragment.shader"

class BillboardShaderSettings : DefaultShaderSettings {
    override val attributes: List<Pair<Int, String>> = emptyList()
    override val program = ShaderManager.compileProgram(vtxSource, frgSource, attributes)
    private val locationPos: Int = ShaderManager.getUniformLocation("sphereCenter", program)
    private val locationPrjMatrix: Int = ShaderManager.getUniformLocation("prjMatrix", program)
    private val locationViewMatrix: Int = ShaderManager.getUniformLocation("viewMatrix", program)
    private val locationSphereRadius: Int = ShaderManager.getUniformLocation("sphereRadius", program)
    private val locationCameraPos: Int = ShaderManager.getUniformLocation("cameraPos", program)
    private val locationLightPos: Int = ShaderManager.getUniformLocation("lightPos", program)
    private val locationLightCol: Int = ShaderManager.getUniformLocation("lightCol", program)
    private val locationAmbient: Int = ShaderManager.getUniformLocation("ambient", program)
    private val locationColor: Int = ShaderManager.getUniformLocation("color", program)
    private val locationShineDamper: Int = ShaderManager.getUniformLocation("shineDamper", program)
    private val locationReflectivity: Int = ShaderManager.getUniformLocation("reflectivity", program)
    private val locationUseTexture: Int = ShaderManager.getUniformLocation("frUseTexture", program)

    private val locationPrjMatrixFr: Int = ShaderManager.getUniformLocation("frPrjMatrix", program)
    private val locationViewMatrixFr: Int = ShaderManager.getUniformLocation("frViewMatrix", program)
    private val locationSphereRadiusFr: Int = ShaderManager.getUniformLocation("frSphereRadius", program)
    private val locationCameraPosFr: Int = ShaderManager.getUniformLocation("frCameraPos", program)
    private val locationPosFr: Int = ShaderManager.getUniformLocation("frSphereCenter", program)

    override fun loadTransformationMatrix(transformationMatrix: Matrix4f) {
        return
    }

    override fun loadProjectionMatrix(projectionMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locationPrjMatrix, projectionMatrix)
        ShaderManager.loadMatrix(locationPrjMatrixFr, projectionMatrix)
    }

    override fun loadViewMatrix(viewMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locationViewMatrix, viewMatrix)
        ShaderManager.loadMatrix(locationViewMatrixFr, viewMatrix)
    }

    override fun loadLight(light: Light) {
        ShaderManager.loadVector3(locationLightPos, light.position)
        ShaderManager.loadVector3(locationLightCol, light.color)
        ShaderManager.loadFloat(locationAmbient, light.ambient)
    }

    override fun loadSpecularValues(damper: Float, reflectivity: Float) {
        ShaderManager.loadFloat(locationShineDamper, damper)
        ShaderManager.loadFloat(locationReflectivity, reflectivity)
    }

    override fun loadCameraPosition(cameraPosition: Vector3f) {
        ShaderManager.loadVector3(locationCameraPos, cameraPosition)
        ShaderManager.loadVector3(locationCameraPosFr, cameraPosition)
    }

    override fun loadColor(color: Vector3f) {
        ShaderManager.loadVector3(locationColor, color);
    }

    fun loadSphereCenter(sphereCenter: Vector3f) {
        ShaderManager.loadVector3(locationPos, sphereCenter)
        ShaderManager.loadVector3(locationPosFr, sphereCenter)
    }

    fun loadSphereRadius(radius: Float) {
        ShaderManager.loadFloat(locationSphereRadius, radius)
        ShaderManager.loadFloat(locationSphereRadiusFr, radius)
    }

    fun loadUseTexture(useTexture: Boolean) {
        ShaderManager.loadBoolean(locationUseTexture, useTexture)
    }

}