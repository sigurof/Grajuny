package no.sigurof.tutorial.lwjgl.shaders

import no.sigurof.tutorial.lwjgl.resource.ResourceGl
import no.sigurof.tutorial.lwjgl.shaders.settings.ShaderSettings
import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL20.GL_COMPILE_STATUS
import org.lwjgl.opengl.GL20.GL_FALSE
import org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER
import org.lwjgl.opengl.GL20.GL_LINK_STATUS
import org.lwjgl.opengl.GL20.GL_VERTEX_SHADER
import org.lwjgl.opengl.GL20.glAttachShader
import org.lwjgl.opengl.GL20.glBindAttribLocation
import org.lwjgl.opengl.GL20.glCompileShader
import org.lwjgl.opengl.GL20.glCreateProgram
import org.lwjgl.opengl.GL20.glCreateShader
import org.lwjgl.opengl.GL20.glDeleteProgram
import org.lwjgl.opengl.GL20.glDeleteShader
import org.lwjgl.opengl.GL20.glDetachShader
import org.lwjgl.opengl.GL20.glGetProgramInfoLog
import org.lwjgl.opengl.GL20.glGetProgrami
import org.lwjgl.opengl.GL20.glGetShaderInfoLog
import org.lwjgl.opengl.GL20.glGetShaderi
import org.lwjgl.opengl.GL20.glGetUniformLocation
import org.lwjgl.opengl.GL20.glLinkProgram
import org.lwjgl.opengl.GL20.glShaderSource
import org.lwjgl.opengl.GL20.glUniform1f
import org.lwjgl.opengl.GL20.glUniform2f
import org.lwjgl.opengl.GL20.glUniform3f
import org.lwjgl.opengl.GL20.glUniformMatrix4fv
import org.lwjgl.opengl.GL20.glUseProgram
import org.lwjgl.opengl.GL30
import java.io.File
import java.nio.FloatBuffer

abstract class ShaderCommon<S : ShaderSettings> constructor(vtxSource: String, frgSource: String) {

    fun usingVaoDo(vao: ResourceGl<*>, function: () -> Unit) {
        this.start()
        this.bindVertAttrArrayAndVao(vao.getVao())
        function()
        this.unbindVertAttrArrayAndVao()
        this.stop()
    }

    private val boundAttribs = mutableListOf<Int>()

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
    private val program: Int = glCreateProgram().run {
        glAttachShader(this, vtxShader)
        glAttachShader(this, frgShader)
        bindAttributes()
        glLinkProgram(this)
        if (glGetProgrami(this, GL_LINK_STATUS) == GL_FALSE) {
            val info = glGetProgramInfoLog(this, 512)
            throw RuntimeException("Feil ved linking av shadere:\n $info")
        }
        return@run this
    }

    protected fun getUniformLocation(uniformName: String): Int {
        return glGetUniformLocation(program, uniformName)
    }

    private fun start() {
        glUseProgram(program)
    }

    private fun stop() {
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

    protected fun loadVector2(location: Int, vector: Vector2f) {
        glUniform2f(location, vector.x, vector.y)
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
    private fun bindVertAttrArrayAndVao(vao: Int) {
        // TODO If the same vao is used by several models, this call should be moved
        GL30.glBindVertexArray(vao) // TODO Should maybe move this to shader
        for (attr in boundAttribs) {
            GL30.glEnableVertexAttribArray(attr)
        }
    }

    private fun unbindVertAttrArrayAndVao() {
        for (attr in boundAttribs) {
            GL30.glDisableVertexAttribArray(attr)
        }
        // TODO If the same vao is used by several models, this call should be moved
        GL30.glBindVertexArray(0)
    }

    companion object {

        private fun compileShaderFromSource(source: String, typeGl: Int): Int {
            val text: String = File(source).readText()
            val shader = glCreateShader(typeGl)
            glShaderSource(shader, text)
            glCompileShader(shader)
            if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
                val info = glGetShaderInfoLog(shader, 512)
                val hvilken = when (typeGl) {
                    GL_VERTEX_SHADER -> "vertex"
                    GL_FRAGMENT_SHADER -> "fragment"
                    else -> "ukjent"
                }
                throw RuntimeException("Feil ved kompilering av $hvilken shader:\n $info")
            }
            return shader
        }
    }
}

