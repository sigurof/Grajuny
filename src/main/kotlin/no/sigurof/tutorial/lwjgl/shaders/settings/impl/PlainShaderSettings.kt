package no.sigurof.tutorial.lwjgl.shaders.settings.impl

import no.sigurof.tutorial.lwjgl.entity.Light
import no.sigurof.tutorial.lwjgl.shaders.ShaderCommon
import no.sigurof.tutorial.lwjgl.shaders.settings.DefaultShaderSettings
import org.joml.Matrix4f
import org.joml.Vector3f

private const val vtxSource = "src/main/resources/shader/texture/vertex.shader"
private const val frgSource = "src/main/resources/shader/texture/fragment.shader"

object PlainShaderSettings : DefaultShaderSettings, ShaderCommon<PlainShaderSettings>(
    vtxSource,
    frgSource
) {
    private val locationTrMatrix: Int = getUniformLocation("trMatrix")
    private val locationPrjMatrix: Int = getUniformLocation("prjMatrix")
    private val locationViewMatrix: Int = getUniformLocation("viewMatrix")
    private val locationLightPos: Int = getUniformLocation("lightPos")
    private val locationLightCol: Int = getUniformLocation("lightCol")
    private val locationShineDamper: Int = getUniformLocation("shineDamper")
    private val locationReflectivity: Int = getUniformLocation("reflectivity")
    private val locationAmbient: Int = getUniformLocation("ambient")
    private val locationColor: Int = getUniformLocation("color")
    private val locationCameraPos: Int = getUniformLocation("cameraPos")
    public override fun bindAttributes() {
        bindAttribute(0, "position")
        bindAttribute(2, "normal")
    }

    override fun loadTransformationMatrix(transformationMatrix: Matrix4f) {
        loadMatrix(locationTrMatrix, transformationMatrix)
    }

    override fun loadProjectionMatrix(projectionMatrix: Matrix4f) {
        loadMatrix(locationPrjMatrix, projectionMatrix)
    }

    override fun loadViewMatrix(viewMatrix: Matrix4f) {
        loadMatrix(locationViewMatrix, viewMatrix)
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

    override fun loadColor(color: Vector3f) {
        loadVector3(locationColor, color);
    }

    override fun loadCameraPosition(cameraPosition: Vector3f) {
        loadVector3(locationCameraPos, cameraPosition)
    }
}