package no.sigurof.tutorial.lwjgl.shaders.settings.impl

import no.sigurof.tutorial.lwjgl.entity.Light
import no.sigurof.tutorial.lwjgl.shaders.ShaderCommon
import no.sigurof.tutorial.lwjgl.shaders.settings.DefaultShaderSettings
import org.joml.Matrix4f
import org.joml.Vector3f

private const val vtxSource = "src/main/resources/shader/billboard/vertex.shader"
private const val frgSource = "src/main/resources/shader/billboard/fragment.shader"

object BillboardShaderSettings : DefaultShaderSettings, ShaderCommon<BillboardShaderSettings>(
    vtxSource,
    frgSource
) {

    private val locationPos: Int = getUniformLocation("sphereCenter")
    private val locationPrjMatrix: Int = getUniformLocation("prjMatrix")
    private val locationViewMatrix: Int = getUniformLocation("viewMatrix")
    private val locationSphereRadius: Int = getUniformLocation("sphereRadius")
    private val locationCameraPos: Int = getUniformLocation("cameraPos")
    private val locationLightPos: Int = getUniformLocation("lightPos")
    private val locationLightCol: Int = getUniformLocation("lightCol")
    private val locationAmbient: Int = getUniformLocation("ambient")
    private val locationColor: Int = getUniformLocation("color")
    private val locationShineDamper: Int = getUniformLocation("shineDamper")
    private val locationReflectivity: Int = getUniformLocation("reflectivity")

    private val locationPrjMatrixFr: Int = getUniformLocation("frPrjMatrix")
    private val locationViewMatrixFr: Int = getUniformLocation("frViewMatrix")
    private val locationSphereRadiusFr: Int = getUniformLocation("frSphereRadius")
    private val locationCameraPosFr: Int = getUniformLocation("frCameraPos")
    private val locationPosFr: Int = getUniformLocation("frSphereCenter")

    override fun bindAttributes() {
        return
    }

    override fun loadTransformationMatrix(transformationMatrix: Matrix4f) {
        return
    }

    override fun loadProjectionMatrix(projectionMatrix: Matrix4f) {
        loadMatrix(locationPrjMatrix, projectionMatrix)
        loadMatrix(locationPrjMatrixFr, projectionMatrix)
    }

    override fun loadViewMatrix(viewMatrix: Matrix4f) {
        loadMatrix(locationViewMatrix, viewMatrix)
        loadMatrix(locationViewMatrixFr, viewMatrix)
    }

    override fun loadLight(light: Light) {
        loadVector3(locationLightPos, light.position)
        loadVector3(locationLightCol, light.color)
        loadFloat(locationAmbient, light.ambient)
    }

    override fun loadSpecularValues(damper: Float, reflectivity: Float) {
        loadFloat(locationShineDamper, damper)
        loadFloat(locationReflectivity, reflectivity)
    }

    override fun loadCameraPosition(cameraPosition: Vector3f) {
        loadVector3(locationCameraPos, cameraPosition)
        loadVector3(locationCameraPosFr, cameraPosition)
    }

    override fun loadColor(color: Vector3f) {
        loadVector3(locationColor, color);
    }

    fun loadSphereCenter(sphereCenter: Vector3f) {
        loadVector3(locationPos, sphereCenter)
        loadVector3(locationPosFr, sphereCenter)
    }

    fun loadSphereRadius(radius: Float) {
        loadFloat(locationSphereRadius, radius)
        loadFloat(locationSphereRadiusFr, radius)
    }


}