package no.sigurof.grajuny.restructuring

import no.sigurof.grajuny.shaders.ShaderManager
import org.joml.Matrix4f
import org.joml.Vector3f

private const val vtxSource = "/shader/texture/vertex.shader"
private const val frgSource = "/shader/texture/fragment.shader"

object BasicShader : Shader() {
    val attributes = listOf(
        0 to "position",
        1 to "textureCoords",
        2 to "normal"
    )
    private val uniforms = listOf(
        "trMatrix",
        "prjMatrix",
        "viewMatrix",
        "lightPos",
        "cameraPos",

        "lightCol",
        "shineDamper",
        "reflectivity",
        "ambient",
        "color",
        "useTexture"
    )
    override val program: Int = ShaderManager.compileProgram(vtxSource, frgSource, attributes)
    override val locations = uniforms.map { it to ShaderManager.getUniformLocation(it, program) }.toMap()

    init {
        addVertexShaderFromFile("basicVertex120.vs")
        addFragmentShaderFromFile("basicVertex120.vs")
        compileShader()

        addUniform("transform")
        addUniform("color")
        TShaderManager.addShader(this)
    }

    fun loadTransformationMatrix(transformationMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locations.getValue("trMatrix"), transformationMatrix)
    }

    fun loadProjectionMatrix(projectionMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locations.getValue("prjMatrix"), projectionMatrix)
    }

    fun loadViewMatrix(viewMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locations.getValue("viewMatrix"), viewMatrix)
    }

    fun loadSpecularValues(damper: Float, reflectivity: Float) {
        ShaderManager.loadFloat(locations.getValue("shineDamper"), damper)
        ShaderManager.loadFloat(locations.getValue("reflectivity"), reflectivity)
    }

    fun loadCameraPosition(cameraPosition: Vector3f) {
        ShaderManager.loadVector3(locations.getValue("cameraPos"), cameraPosition)
    }

    fun loadColor(color: Vector3f) {
        ShaderManager.loadVector3(locations.getValue("color"), color);
    }

    fun loadLight(lightSource: LightSource) {
        ShaderManager.loadVector3(locations.getValue("lightPos"), lightSource.position)
        ShaderManager.loadVector3(locations.getValue("lightCol"), lightSource.color)
        ShaderManager.loadFloat(locations.getValue("ambient"), lightSource.ambient)
    }

    fun loadUseTexture(useTexture: Boolean) {
        ShaderManager.loadBoolean(locations.getValue("useTexture"), useTexture)
    }

}