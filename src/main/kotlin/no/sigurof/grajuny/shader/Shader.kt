package no.sigurof.grajuny.shader

import org.lwjgl.opengl.GL20

abstract class Shader(
    vtxSource: String,
    frgSource: String,
    attributes: List<Pair<Int, String>>,
    uniforms: List<String>
) {

    private val program: Int = ShaderManager.compileProgram(vtxSource, frgSource, attributes)
    val locations = uniforms.map { it to ShaderManager.getUniformLocation(it, program) }.toMap()

    fun cleanUp() {
        GL20.glUseProgram(0)
        GL20.glDeleteProgram(program)
    }

    fun use() {
        GL20.glUseProgram(program)
    }

}