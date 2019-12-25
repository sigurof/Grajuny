package no.sigurof.tutorial.lwjgl.shaders

import no.sigurof.tutorial.lwjgl.entity.Camera
import no.sigurof.tutorial.lwjgl.utils.Maths
import org.joml.Matrix4f

class TextureShader : ShaderProgram(vtxSource, frgSource) {

    private val locationTrMatrix: Int = getUniformLocation("trMatrix")
    private val locationPrjMatrix: Int = getUniformLocation("prjMatrix")
    private val locationViewMatrix: Int = getUniformLocation("viewMatrix")


    companion object {
        private val vtxSource = "src/main/resources/shader/texture/vertex.shader"
        private val frgSource = "src/main/resources/shader/texture/fragment.shader"
    }

    public override fun bindAttributes() {
        bindAttribute(0, "position")
        bindAttribute(1, "textureCoords")
    }

    fun loadTransformationMatrix(matrix4f: Matrix4f) {
        loadMatrix(locationTrMatrix, matrix4f)
    }


    fun loadProjectionMatrix(matrix4f: Matrix4f) {
        loadMatrix(locationPrjMatrix, matrix4f)
    }

    fun loadViewMatrix(camera: Camera) {
        val matrix4f = Maths.createViewMatrix(camera)
        loadMatrix(locationViewMatrix, matrix4f)
    }
}