package no.sigurof.grajuny.resource

import no.sigurof.grajuny.resource.mesh.Loader
import org.joml.Vector3f
import org.lwjgl.opengl.GL11.GL_LINES
import org.lwjgl.opengl.GL11.GL_UNSIGNED_INT
import org.lwjgl.opengl.GL11.glDrawElements
import org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW
import org.lwjgl.opengl.GL30.glBindVertexArray
import org.lwjgl.opengl.GL30.glDisableVertexAttribArray
import org.lwjgl.opengl.GL30.glEnableVertexAttribArray

class LineResource private constructor(
    private val numElements: Int,
    private val attributeNumber: Int,
    private val vao: Int
) {

    fun render() {
        glDrawElements(GL_LINES, numElements, GL_UNSIGNED_INT, 0)
    }

    fun activate() {
        glBindVertexArray(vao)
        glEnableVertexAttribArray(attributeNumber)
    }

    fun deactivate() {
        glDisableVertexAttribArray(attributeNumber)
        glBindVertexArray(0)
    }

    companion object {
        fun fromPoints(vertices: MutableList<Vector3f>): LineResource {
            val numLines = vertices.size - 1
            val indices = (0 until numLines).flatMap { listOf(it, it + 1) }.toIntArray()
            val vao = Loader.createVao()
            glBindVertexArray(vao)
            Loader.bindIndicesBuffer(indices, GL_DYNAMIC_DRAW)
            val attributeNumber = 0
            Loader.storeDataInAttributeList(
                attributeNumber = attributeNumber,
                coordinateSize = 3,
                data = vertices.flatMap { listOf(it.x, it.y, it.z) }.toFloatArray()
            )
            glBindVertexArray(0)
            return LineResource(
                numElements = indices.size,
                attributeNumber = attributeNumber,
                vao = vao
            )
        }
    }

}