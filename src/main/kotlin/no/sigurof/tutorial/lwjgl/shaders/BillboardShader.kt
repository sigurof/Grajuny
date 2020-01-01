package no.sigurof.tutorial.lwjgl.shaders

import no.sigurof.tutorial.lwjgl.entity.Light
import org.joml.Matrix4f
import org.joml.Vector3f

private const val vtxSource = "src/main/resources/shader/billboard/vertex.shader"
private const val frgSource = "src/main/resources/shader/billboard/fragment.shader"

object BillboardShader : ShaderProgram(vtxSource, frgSource) {

    private val locationPos: Int = getUniformLocation("pos")
    private val locationPrjMatrix: Int = getUniformLocation("prjMatrix")
    private val locationViewMatrix: Int = getUniformLocation("viewMatrix")
    private val locationSphereRadius: Int = getUniformLocation("sphereRadius")

    private val locationLightPos: Int = getUniformLocation("lightPos")
    private val locationLightCol: Int = getUniformLocation("lightCol")
    private val locationAmbient: Int = getUniformLocation("ambient")
    private val locationColor: Int = getUniformLocation("color")
    private val locationShineDamper: Int = getUniformLocation("shineDamper")
    private val locationReflectivity: Int = getUniformLocation("reflectivity")
    override fun bindAttributes() {
        return
    }

    fun loadPos(pos: Vector3f) {
        loadVector3(locationPos, pos)
    }

    fun loadProjectionMatrix(matrix4f: Matrix4f) {
        loadMatrix(locationPrjMatrix, matrix4f)
    }

    fun loadViewMatrix(matrix4f: Matrix4f) {
        loadMatrix(locationViewMatrix, matrix4f)
    }

    fun loadSphereRadius(radius: Float) {
        loadFloat(locationSphereRadius, radius)
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