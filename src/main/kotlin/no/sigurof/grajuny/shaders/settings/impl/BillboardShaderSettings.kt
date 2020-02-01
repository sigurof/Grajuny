package no.sigurof.grajuny.shaders.settings.impl

import no.sigurof.grajuny.entity.Light
import no.sigurof.grajuny.resource.ResourceGl
import no.sigurof.grajuny.shaders.settings.DefaultShaderSettings
import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30
import java.nio.FloatBuffer

private const val vtxSource = "/shader/billboard/vertex.shader"
private const val frgSource = "/shader/billboard/fragment.shader"

class BillboardShaderSettings : DefaultShaderSettings {
    private val vtxShader: Int =
        compileShaderFromSource(
            vtxSource,
            GL20.GL_VERTEX_SHADER
        )
    private val frgShader: Int =
        compileShaderFromSource(
            frgSource,
            GL20.GL_FRAGMENT_SHADER
        )
    private val program: Int = GL20.glCreateProgram().let {
        GL20.glAttachShader(it, vtxShader)
        GL20.glAttachShader(it, frgShader)
        bindAttributes()
        GL20.glLinkProgram(it)
        if (GL20.glGetProgrami(it, GL20.GL_LINK_STATUS) == GL20.GL_FALSE) {
            val info = GL20.glGetProgramInfoLog(it, 512)
            throw RuntimeException("Feil ved linking av shadere:\n $info")
        }
        it
    }
    private val locationPos: Int = getUniformLocation("sphereCenter")
    private val locationPrjMatrix: Int = getUniformLocation("prjMatrix")
    private val locationViewMatrix: Int = getUniformLocation("viewMatrix")
    private val locationSphereRadius: Int = getUniformLocation("sphereRadius")
    private val locationCameraPos: Int = getUniformLocation("cameraPos")
    private val locationLightPos: Int = getUniformLocation("lightPos")
    private val locationLightCol: Int = getUniformLocation("lightCol")
    private val locationAmbient: Int = getUniformLocation("ambient")
    private val locationColor: Int = getUniformLocation("color")
    private val locationShineDamper: Int = getUniformLocation("shineDamper")
    private val locationReflectivity: Int = getUniformLocation("reflectivity")
    private val locationUseTexture: Int = getUniformLocation("frUseTexture")

    private val locationPrjMatrixFr: Int = getUniformLocation("frPrjMatrix")
    private val locationViewMatrixFr: Int = getUniformLocation("frViewMatrix")
    private val locationSphereRadiusFr: Int = getUniformLocation("frSphereRadius")
    private val locationCameraPosFr: Int = getUniformLocation("frCameraPos")
    private val locationPosFr: Int = getUniformLocation("frSphereCenter")

    fun bindAttributes() {
        return
    }

    override fun loadTransformationMatrix(transformationMatrix: Matrix4f) {
        return
    }

    override fun loadProjectionMatrix(projectionMatrix: Matrix4f) {
        loadMatrix(locationPrjMatrix, projectionMatrix)
        loadMatrix(locationPrjMatrixFr, projectionMatrix)
    }

    override fun loadViewMatrix(viewMatrix: Matrix4f) {
        loadMatrix(locationViewMatrix, viewMatrix)
        loadMatrix(locationViewMatrixFr, viewMatrix)
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

    override fun loadCameraPosition(cameraPosition: Vector3f) {
        loadVector3(locationCameraPos, cameraPosition)
        loadVector3(locationCameraPosFr, cameraPosition)
    }

    override fun loadColor(color: Vector3f) {
        loadVector3(locationColor, color);
    }

    fun loadSphereCenter(sphereCenter: Vector3f) {
        loadVector3(locationPos, sphereCenter)
        loadVector3(locationPosFr, sphereCenter)
    }

    fun loadSphereRadius(radius: Float) {
        loadFloat(locationSphereRadius, radius)
        loadFloat(locationSphereRadiusFr, radius)
    }

    fun loadUseTexture(useTexture: Boolean) {
        loadBoolean(locationUseTexture, useTexture)
    }

    override fun usingVaoDo(vao: ResourceGl<*>, function: () -> Unit) {
        this.start()
        this.bindVertAttrArrayAndVao(vao.getVao())
        function()
        this.unbindVertAttrArrayAndVao()
        this.stop()
    }

    private val boundAttribs = mutableListOf<Int>()

    private val matrixBuffer: FloatBuffer = BufferUtils.createFloatBuffer(16)

    private fun getUniformLocation(uniformName: String): Int {
        return GL20.glGetUniformLocation(program, uniformName)
            .takeIf { it != -1 }
            ?: error("Uniform $uniformName does not have an active location.")
    }

    private fun start() {
        GL20.glUseProgram(program)
    }

    private fun stop() {
        GL20.glUseProgram(0)
    }

    override fun cleanUp() {
        stop()
        GL20.glDetachShader(program, vtxShader)
        GL20.glDetachShader(program, frgShader)
        GL20.glDeleteShader(vtxShader)
        GL20.glDeleteShader(frgShader)
        GL20.glDeleteProgram(program)
    }

    fun bindAttribute(attributeIdx: Int, variableName: String) {
        GL20.glBindAttribLocation(program, attributeIdx, variableName)
        boundAttribs.add(attributeIdx)
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

    private fun compileShaderFromSource(source: String, typeGl: Int): Int {
        val text = BillboardShaderSettings::class.java.getResource(source).readText()
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
}