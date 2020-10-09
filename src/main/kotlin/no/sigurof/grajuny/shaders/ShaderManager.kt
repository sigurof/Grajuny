package no.sigurof.grajuny.shaders

import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL20
import java.nio.FloatBuffer

object ShaderManager {
    private val matrixBuffer: FloatBuffer = BufferUtils.createFloatBuffer(16)

    fun getUniformLocation(uniformName: String, program: Int): Int {
        return GL20.glGetUniformLocation(program, uniformName)
            .takeIf { it != -1 }
            ?: error("Uniform $uniformName does not have an active location.")
    }

    fun compileProgram(vtxSource: String, frgSource: String, attributes: List<Pair<Int, String>>): Int {
        val program = GL20.glCreateProgram()
        val vtxShader: Int =
            compileShaderFromSource(
                vtxSource,
                GL20.GL_VERTEX_SHADER
            )
        val frgShader: Int =
            compileShaderFromSource(
                frgSource,
                GL20.GL_FRAGMENT_SHADER
            )
        GL20.glAttachShader(program, vtxShader)
        GL20.glAttachShader(program, frgShader)
        for (attribute in attributes) {
            bindAttribute(attribute.first, attribute.second, program)
        }
        GL20.glLinkProgram(program)
        if (GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) == GL20.GL_FALSE) {
            val info = GL20.glGetProgramInfoLog(program, 512)
            throw RuntimeException("Feil ved linking av shadere:\n $info")
        }
        GL20.glDetachShader(program, vtxShader)
        GL20.glDetachShader(program, frgShader)
        GL20.glDeleteShader(vtxShader)
        GL20.glDeleteShader(frgShader)
        return program
    }

    private fun compileShaderFromSource(source: String, typeGl: Int): Int {
        val text = ShaderManager::class.java.getResource(source).readText()
        val shader = GL20.glCreateShader(typeGl)
        GL20.glShaderSource(shader, text)
        GL20.glCompileShader(shader)
        if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == GL20.GL_FALSE) {
            val info = GL20.glGetShaderInfoLog(shader, 512)
            val hvilken = when (typeGl) {
                GL20.GL_VERTEX_SHADER -> "vertex"
                GL20.GL_FRAGMENT_SHADER -> "fragment"
                else -> "ukjent"
            }
            throw RuntimeException("Feil ved kompilering av $hvilken shader:\n $info")
        }
        return shader
    }

    private fun bindAttribute(attributeIdx: Int, variableName: String, program: Int) {
        GL20.glBindAttribLocation(program, attributeIdx, variableName)
    }

    fun loadFloat(location: Int, value: Float) {
        GL20.glUniform1f(location, value)
    }

    fun loadVector3(location: Int, vector: Vector3f) {
        GL20.glUniform3f(location, vector.x, vector.y, vector.z)
    }

    fun loadVector2(location: Int, vector: Vector2f) {
        GL20.glUniform2f(location, vector.x, vector.y)
    }

    fun loadBoolean(location: Int, value: Boolean) {
        GL20.glUniform1f(
            location, when (value) {
                true -> 1f
                false -> 0f
            }
        )
    }

    fun loadMatrix(location: Int, matrix4f: Matrix4f) {
        matrix4f.get(matrixBuffer)
        GL20.glUniformMatrix4fv(location, false, matrixBuffer)
    }

    fun stop() {
        GL20.glUseProgram(0)
    }

    fun cleanUp(program: Int) {
        stop()
        GL20.glDeleteProgram(program)
    }

}