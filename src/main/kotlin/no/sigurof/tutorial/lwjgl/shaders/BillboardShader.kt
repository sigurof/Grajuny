package no.sigurof.tutorial.lwjgl.shaders

import org.joml.Matrix4f
import org.joml.Vector3f

class BillboardShader : ShaderProgram(vtxSource, frgSource) {

    private val locationPos: Int = getUniformLocation("pos")
    private val locationPrjMatrix: Int = getUniformLocation("prjMatrix")
    private val locationViewMatrix: Int = getUniformLocation("viewMatrix")
    private val locationSphereRadius: Int = getUniformLocation("sphereRadius")

    companion object {
        private const val vtxSource = "src/main/resources/shader/billboard/vertex.shader"
        private const val frgSource = "src/main/resources/shader/billboard/fragment.shader"
    }

    override fun bindAttributes() {
        return
    }

    fun loadPos(pos: Vector3f) {
        loadVector3(locationPos, pos)
    }

    fun loadProjectionMatrix(matrix4f: Matrix4f) {
        loadMatrix(locationPrjMatrix, matrix4f)
    }

    fun loadViewMatrix(matrix4f: Matrix4f) {
        loadMatrix(locationViewMatrix, matrix4f)
    }

    fun loadSphereRadius(radius: Float) {
        loadFloat(locationSphereRadius, radius)
    }

}