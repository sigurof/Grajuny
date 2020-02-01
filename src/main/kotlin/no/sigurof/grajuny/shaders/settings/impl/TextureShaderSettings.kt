package no.sigurof.grajuny.shaders.settings.impl

import no.sigurof.grajuny.entity.Light
import no.sigurof.grajuny.shaders.ShaderManager
import no.sigurof.grajuny.shaders.settings.DefaultShaderSettings
import org.joml.Matrix4f
import org.joml.Vector3f

private const val vtxSource = "/shader/texture/vertex.shader"
private const val frgSource = "/shader/texture/fragment.shader"

object TextureShaderSettings : DefaultShaderSettings {
    override val attributes = listOf(
        0 to "position",
        1 to "textureCoords",
        2 to "normal"
    )
    override val program = ShaderManager.compileProgram(vtxSource, frgSource, attributes)
    private val locationTrMatrix: Int = ShaderManager.getUniformLocation("trMatrix", program)
    private val locationPrjMatrix: Int = ShaderManager.getUniformLocation("prjMatrix", program)
    private val locationViewMatrix: Int = ShaderManager.getUniformLocation("viewMatrix", program)
    private val locationLightPos: Int = ShaderManager.getUniformLocation("lightPos", program)
    private val locationLightCol: Int = ShaderManager.getUniformLocation("lightCol", program)
    private val locationShineDamper: Int = ShaderManager.getUniformLocation("shineDamper", program)
    private val locationReflectivity: Int = ShaderManager.getUniformLocation("reflectivity", program)
    private val locationAmbient: Int = ShaderManager.getUniformLocation("ambient", program)
    private val locationColor: Int = ShaderManager.getUniformLocation("color", program)
    private val locationCameraPosition: Int = ShaderManager.getUniformLocation("cameraPos", program)
    private val locationUseTexture: Int = ShaderManager.getUniformLocation("useTexture", program)

    override fun loadTransformationMatrix(transformationMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locationTrMatrix, transformationMatrix)
    }

    override fun loadProjectionMatrix(projectionMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locationPrjMatrix, projectionMatrix)
    }

    override fun loadViewMatrix(viewMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locationViewMatrix, viewMatrix)
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

    override fun loadColor(color: Vector3f) {
        ShaderManager.loadVector3(locationColor, color);
    }

    override fun loadCameraPosition(cameraPosition: Vector3f) {
        ShaderManager.loadVector3(locationCameraPosition, cameraPosition)
    }

    fun loadUseTexture(useTexture: Boolean) {
        ShaderManager.loadBoolean(locationUseTexture, useTexture)
    }



}

