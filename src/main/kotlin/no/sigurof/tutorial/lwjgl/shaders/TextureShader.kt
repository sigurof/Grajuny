package no.sigurof.tutorial.lwjgl.shaders

import no.sigurof.tutorial.lwjgl.entity.Light
import org.joml.Matrix4f

private const val vtxSource = "src/main/resources/shader/texture/vertex.shader"
private const val frgSource = "src/main/resources/shader/texture/fragment.shader"

object TextureShader : ShaderProgram(vtxSource, frgSource) {

    private val locationTrMatrix: Int = getUniformLocation("trMatrix")
    private val locationPrjMatrix: Int = getUniformLocation("prjMatrix")
    private val locationViewMatrix: Int = getUniformLocation("viewMatrix")
    private val locationLightPos: Int = getUniformLocation("lightPos")
    private val locationLightCol: Int = getUniformLocation("lightCol")
    private val locationShineDamper: Int = getUniformLocation("shineDamper")
    private val locationReflectivity: Int = getUniformLocation("reflectivity")
    private val locationAmbient: Int = getUniformLocation("ambient")

    public override fun bindAttributes() {
        bindAttribute(0, "position")
        bindAttribute(1, "textureCoords")
        bindAttribute(2, "normal")
    }

    fun loadTransformationMatrix(transformationMatrix: Matrix4f) {
        loadMatrix(locationTrMatrix, transformationMatrix)
    }

    fun loadProjectionMatrix(projectionMatrix: Matrix4f) {
        loadMatrix(locationPrjMatrix, projectionMatrix)
    }

    fun loadViewMatrix(viewMatrix: Matrix4f) {
        loadMatrix(locationViewMatrix, viewMatrix)
    }

    fun loadLight(light: Light) {
        loadVector3(locationLightPos, light.position)
        loadVector3(locationLightCol, light.color)
        loadFloat(locationAmbient, light.ambient)
    }

    fun loadSpecularValues(damper: Float, reflectivity: Float) {
        loadFloat(locationShineDamper, damper)
        loadFloat(locationReflectivity, reflectivity)
    }
}

