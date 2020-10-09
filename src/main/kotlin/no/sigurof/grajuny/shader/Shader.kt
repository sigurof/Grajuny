package no.sigurof.grajuny.shader

import org.lwjgl.opengl.GL20

abstract class Shader {

    abstract val program: Int
    abstract val locations: Map<String, Int>

    fun cleanUp() {
        GL20.glUseProgram(0)
        GL20.glDeleteProgram(program)
    }

    fun use() {
        GL20.glUseProgram(program)
    }

}