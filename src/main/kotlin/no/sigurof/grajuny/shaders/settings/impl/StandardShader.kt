package no.sigurof.grajuny.shaders.settings.impl

import no.sigurof.grajuny.entity.Light
import no.sigurof.grajuny.shaders.ShaderManager
import no.sigurof.grajuny.shaders.settings.DefaultShaderSettings
import org.joml.Matrix4f
import org.joml.Vector3f

private const val vtxSource = "/shader/texture/vertex.shader"
private const val frgSource = "/shader/texture/fragment.shader"

object StandardShader : DefaultShaderSettings {
    override val attributes = listOf(
        0 to "position",
        1 to "textureCoords",
        2 to "normal"
    )
    override val program = ShaderManager.compileProgram(vtxSource, frgSource, attributes)
    private val uniforms = listOf(
        "trMatrix",
        "prjMatrix",
        "viewMatrix",
        "lightPos",
        "lightCol",
        "shineDamper",
        "reflectivity",
        "ambient",
        "color",
        "cameraPos",
        "useTexture"
    )
    private val locations = uniforms.map { it to ShaderManager.getUniformLocation(it, program) }.toMap()

    override fun loadTransformationMatrix(transformationMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locations.getValue("trMatrix"), transformationMatrix)
    }

    override fun loadProjectionMatrix(projectionMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locations.getValue("prjMatrix"), projectionMatrix)
    }

    override fun loadViewMatrix(viewMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locations.getValue("viewMatrix"), viewMatrix)
    }

    override fun loadLight(light: Light) {
        ShaderManager.loadVector3(locations.getValue("lightPos"), light.position)
        ShaderManager.loadVector3(locations.getValue("lightCol"), light.color)
        ShaderManager.loadFloat(locations.getValue("ambient"), light.ambient)
    }

    override fun loadSpecularValues(damper: Float, reflectivity: Float) {
        ShaderManager.loadFloat(locations.getValue("shineDamper"), damper)
        ShaderManager.loadFloat(locations.getValue("reflectivity"), reflectivity)
    }

    override fun loadColor(color: Vector3f) {
        ShaderManager.loadVector3(locations.getValue("color"), color);
    }

    override fun loadCameraPosition(cameraPosition: Vector3f) {
        ShaderManager.loadVector3(locations.getValue("cameraPos"), cameraPosition)
    }

    fun loadUseTexture(useTexture: Boolean) {
        ShaderManager.loadBoolean(locations.getValue("useTexture"), useTexture)
    }
}

