package no.sigurof.tutorial.lwjgl.shaders

import no.sigurof.tutorial.lwjgl.entity.Light
import org.joml.Matrix4f
import org.joml.Vector3f

private const val vtxSource = "src/main/resources/shader/billboard/vertex.shader"
private const val frgSource = "src/main/resources/shader/billboard/fragment.shader"

object BillboardShader : ShaderProgram(vtxSource, frgSource) {

    private val locationPos: Int = getUniformLocation("sphereCenter")
    private val locationPosFr: Int = getUniformLocation("frSphereCenter")

    private val locationPrjMatrix: Int = getUniformLocation("prjMatrix")
    private val locationViewMatrix: Int = getUniformLocation("viewMatrix")
    private val locationSphereRadius: Int = getUniformLocation("sphereRadius")

    private val locationPrjMatrixFr: Int = getUniformLocation("frPrjMatrix")
    private val locationViewMatrixFr: Int = getUniformLocation("frViewMatrix")
    private val locationSphereRadiusFr: Int = getUniformLocation("frSphereRadius")

    private val locationCameraPos: Int = getUniformLocation("cameraPos")
    private val locationCameraPosFr: Int = getUniformLocation("frCameraPos")

    private val locationLightPos: Int = getUniformLocation("lightPos")
    private val locationLightCol: Int = getUniformLocation("lightCol")
    private val locationAmbient: Int = getUniformLocation("ambient")
    private val locationColor: Int = getUniformLocation("color")
    private val locationShineDamper: Int = getUniformLocation("shineDamper")
    private val locationReflectivity: Int = getUniformLocation("reflectivity")
    override fun bindAttributes() {
        return
    }

    fun loadSphereCenter(sphereCenter: Vector3f) {
        loadVector3(locationPos, sphereCenter)
        loadVector3(locationPosFr, sphereCenter)
    }

    fun loadProjectionMatrix(matrix4f: Matrix4f) {
        loadMatrix(locationPrjMatrix, matrix4f)
        loadMatrix(locationPrjMatrixFr, matrix4f)
    }

    fun loadViewMatrix(matrix4f: Matrix4f) {
        loadMatrix(locationViewMatrix, matrix4f)
        loadMatrix(locationViewMatrixFr, matrix4f)
    }

    fun loadSphereRadius(radius: Float) {
        loadFloat(locationSphereRadius, radius)
        loadFloat(locationSphereRadiusFr, radius)
    }

    fun loadCameraPos(cameraPos: Vector3f) {
        loadVector3(locationCameraPos, cameraPos)
        loadVector3(locationCameraPosFr, cameraPos)
    }

    fun loadLight(light: Light) {
        loadVector3(locationLightPos, light.position)
        loadVector3(locationLightCol, light.color)
        loadFloat(locationAmbient, light.ambient)
    }

    fun loadSurface(damper: Float, reflectivity: Float, color: Vector3f) {
        loadFloat(locationShineDamper, damper)
        loadFloat(locationReflectivity, reflectivity)
        loadVector3(locationColor, color);
    }
}