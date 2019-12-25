package no.sigurof.tutorial.lwjgl.shaders

import no.sigurof.tutorial.lwjgl.entity.Camera
import no.sigurof.tutorial.lwjgl.entity.Light
import no.sigurof.tutorial.lwjgl.utils.Maths
import org.joml.Matrix4f

class TextureShader : ShaderProgram(vtxSource, frgSource) {

    private val locationTrMatrix: Int = getUniformLocation("trMatrix")
    private val locationPrjMatrix: Int = getUniformLocation("prjMatrix")
    private val locationViewMatrix: Int = getUniformLocation("viewMatrix")
    private val locationLightPos: Int = getUniformLocation("lightPos")
    private val locationLightCol: Int = getUniformLocation("lightCol")


    companion object {
        private val vtxSource = "src/main/resources/shader/texture/vertex.shader"
        private val frgSource = "src/main/resources/shader/texture/fragment.shader"
    }

    public override fun bindAttributes() {
        bindAttribute(0, "position")
        bindAttribute(1, "textureCoords")
        bindAttribute(2, "normal")
    }

    fun loadTransformationMatrix(transformationMatrix: Matrix4f) {
        loadMatrix(locationTrMatrix, transformationMatrix)
    }

    fun loadProjectionMatrix(projectionMatrix: Matrix4f) {
        loadMatrix(locationPrjMatrix, projectionMatrix)
    }

    fun loadViewMatrix(camera: Camera) {
        val matrix4f = Maths.createViewMatrix(camera)
        loadMatrix(locationViewMatrix, matrix4f)
    }

    fun loadLight(light: Light) {
        loadVector3(locationLightPos, light.position)
        loadVector3(locationLightCol, light.color)
    }
}

