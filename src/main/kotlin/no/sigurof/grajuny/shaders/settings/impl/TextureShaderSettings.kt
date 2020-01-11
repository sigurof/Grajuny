package no.sigurof.grajuny.shaders.settings.impl

import no.sigurof.grajuny.entity.Light
import no.sigurof.grajuny.shaders.ShaderCommon
import no.sigurof.grajuny.shaders.settings.DefaultShaderSettings
import org.joml.Matrix4f
import org.joml.Vector3f

private const val vtxSource = "/shader/texture/vertex.shader"
private const val frgSource = "/shader/texture/fragment.shader"

object TextureShaderSettings : DefaultShaderSettings, ShaderCommon<TextureShaderSettings>(
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
    private val locationCameraPosition: Int = getUniformLocation("cameraPos")
    public override fun bindAttributes() {
        bindAttribute(0, "position")
        bindAttribute(1, "textureCoords")
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
        loadVector3(locationCameraPosition, cameraPosition)
    }
}

