package no.sigurof.grajuny.restructuring

import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL20.glUseProgram

abstract class Shader {

    abstract val program: Int
    abstract val locations: Map<String, Int>

    fun addVertexShaderFromFile(file: String) {}
    fun addFragmentShaderFromFile(file: String) {}
    fun compileShader() {}

    fun addUniform(name: String) {}

    internal fun setUniform(name: String, projectedMatrix: Vector3f) {

    }

    internal fun setUniform(name: String, projectedMatrix: Matrix4f) {

    }

    fun cleanUp() {
        GL20.glUseProgram(0)
        GL20.glDeleteProgram(program)
    }

    fun use() {
        glUseProgram(program)
    }


    fun loadFloat(location: Int, value: Float) {
        GL20.glUniform1f(location, value)
    }

    fun loadVector3(location: Int, vector: Vector3f) {
        GL20.glUniform3f(location, vector.x, vector.y, vector.z)
    }

}