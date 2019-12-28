package no.sigurof.tutorial.lwjgl.shaders

import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL20.*
import java.io.File
import java.nio.FloatBuffer

abstract class ShaderProgram constructor(vtxSource: String, frgSource: String) {

    val boundAttribs = mutableListOf<Int>()

    private val matrixBuffer: FloatBuffer = BufferUtils.createFloatBuffer(16)

    private val vtxShader: Int =
        compileShaderFromSource(
            vtxSource,
            GL_VERTEX_SHADER
        )
    private val frgShader: Int =
        compileShaderFromSource(
            frgSource,
            GL_FRAGMENT_SHADER
        )
    val program: Int = glCreateProgram().run {
        glAttachShader(this, vtxShader)
        glAttachShader(this, frgShader)
        bindAttributes()
        // Trenger ikke følgende kall, da det uansett gjøres i den spesifikke implementasjons initialisering
        // getAllUniformLocations()
        glLinkProgram(this)
        if (glGetProgrami(this, GL_LINK_STATUS) == GL_FALSE) {
            val info = glGetProgramInfoLog(this, 512)
            throw RuntimeException("Feil ved linking av shadere:\n $info")
        }
        return@run this
    }

    // Trenger ikke denne, da jeg holder en oversikt over alle
//    protected abstract fun getAllUniformLocations()

    protected fun getUniformLocation(uniformName: String): Int {
        return glGetUniformLocation(program, uniformName)
    }

    fun start() {
        glUseProgram(program)
    }


    fun stop() {
        glUseProgram(0)
    }

    fun cleanUp() {
        stop()
        glDetachShader(program, vtxShader)
        glDetachShader(program, frgShader)
        glDeleteShader(vtxShader)
        glDeleteShader(frgShader)
        glDeleteProgram(program)

    }

    protected fun bindAttribute(attributeIdx: Int, variableName: String) {
        glBindAttribLocation(program, attributeIdx, variableName)
        boundAttribs.add(attributeIdx)
    }

    protected fun loadFloat(location: Int, value: Float) {
        glUniform1f(location, value)
    }

    protected fun loadVector3(location: Int, vector: Vector3f) {
        glUniform3f(location, vector.x, vector.y, vector.z)
    }

    protected fun loadBoolean(location: Int, value: Boolean) {
        glUniform1f(
            location, when (value) {
                true -> 1f
                false -> 0f
            }
        )
    }

    protected fun loadMatrix(location: Int, matrix4f: Matrix4f) {
        matrix4f.get(matrixBuffer)
        glUniformMatrix4fv(location, false, matrixBuffer)
    }

    protected abstract fun bindAttributes()

    companion object {

        private fun compileShaderFromSource(source: String, typeGl: Int): Int {
            val text: String = File(source).readText()
            val shader = glCreateShader(typeGl)
            glShaderSource(shader, text)
            glCompileShader(shader)
            if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
                val info = glGetShaderInfoLog(shader, 512)
                throw RuntimeException("Feil ved kompilering av shader:\n $info")
            }
            return shader
        }
    }


}

