package no.sigurof.grajuny.shaders.settings.impl

import no.sigurof.grajuny.shaders.ShaderManager
import org.joml.Matrix4f

private const val vtxSource = "/shader/light/vertex.shader"
private const val frgSource = "/shader/light/fragment.shader"

object LightShader : BasicShaderSettings {
    override val attributes = listOf(
        0 to "position"
    )
    override val program = ShaderManager.compileProgram(vtxSource, frgSource, attributes)
    private val uniforms = listOf(
        "trMatrix",
        "prjMatrix",
        "viewMatrix"
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

}

