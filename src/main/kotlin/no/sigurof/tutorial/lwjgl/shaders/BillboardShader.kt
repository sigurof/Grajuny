package no.sigurof.tutorial.lwjgl.shaders

import no.sigurof.tutorial.lwjgl.entity.Camera
import no.sigurof.tutorial.lwjgl.utils.Maths
import org.joml.Matrix4f
import org.joml.Vector3f

class BillboardShader : ShaderProgram(vtxSource, frgSource) {

    private val locationPos: Int = getUniformLocation("pos")
    private val locationTrMatrix: Int = getUniformLocation("trMatrix")
    private val locationPrjMatrix: Int = getUniformLocation("prjMatrix")
    private val locationViewMatrix: Int = getUniformLocation("viewMatrix")

    companion object {
        private val vtxSource = "src/main/resources/shader/billboard/vertex.shader"
        private val frgSource = "src/main/resources/shader/billboard/fragment.shader"
    }

    override fun bindAttributes() {
        return
    }

    fun loadPos(pos: Vector3f) {
        loadVector3(locationPos, pos)
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