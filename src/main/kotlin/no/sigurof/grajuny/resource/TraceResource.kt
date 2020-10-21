package no.sigurof.grajuny.resource

import no.sigurof.grajuny.resource.mesh.Loader
import no.sigurof.grajuny.utils.CyclicCounter
import org.joml.Vector3f
import org.lwjgl.opengl.GL11.GL_LINES
import org.lwjgl.opengl.GL11.GL_UNSIGNED_INT
import org.lwjgl.opengl.GL11.glDrawElements
import org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER
import org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW
import org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER
import org.lwjgl.opengl.GL15.glBindBuffer
import org.lwjgl.opengl.GL15.glBufferSubData
import org.lwjgl.opengl.GL30.glBindVertexArray
import org.lwjgl.opengl.GL30.glDisableVertexAttribArray
import org.lwjgl.opengl.GL30.glEnableVertexAttribArray

class TraceResource(
    val vertices: MutableList<Vector3f>
) {

    private val attributeNumber = 0

    private val numVertices = vertices.size
    private val numLines = numVertices - 1
    private val eboIndsLength = 2 * numLines
    private val eboIndsEnd = CyclicCounter.exclusiveMax(eboIndsLength)
    private val vtxEnd = CyclicCounter.exclusiveMax(numVertices)
    private val eboInds = (0 until numLines).flatMap { listOf(it, it + 1) }.toMutableList()
    private val vao = Loader.createVao()
    private val vbo: Int
    private val ebo: Int

    init {
        glBindVertexArray(vao)
        ebo = Loader.bindIndicesBuffer(eboInds.toIntArray(), GL_DYNAMIC_DRAW)
        vbo = Loader.storeDataInAttributeList(
            attributeNumber = attributeNumber,
            coordinateSize = 3,
            data = vertices.flatMap { listOf(it.x, it.y, it.z) }.toFloatArray(),
            usage = GL_DYNAMIC_DRAW
        )
        glBindVertexArray(0)
    }

    fun render() {
        glDrawElements(GL_LINES, eboInds.size, GL_UNSIGNED_INT, 0)
    }

    fun activate() {
        glBindVertexArray(vao)
        glEnableVertexAttribArray(attributeNumber)
    }

    fun deactivate() {
        glDisableVertexAttribArray(attributeNumber)
        glBindVertexArray(0)
    }

    fun addPoint(position: Vector3f) {
        vertices[vtxEnd.current] = position
        val vertxData = listOf(position.x, position.y, position.z).toFloatArray()
//        glBindVertexArray(vao)
        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        glBufferSubData(
            GL_ARRAY_BUFFER,
            vtxEnd.current.toLong() * 3 * 4,
            Loader.storeDataInFloatBuffer(vertxData)
        )
        glBindBuffer(GL_ARRAY_BUFFER, 0)

        val newEboInds = listOf(
            vtxEnd.peekLast(), vtxEnd.current
        )
        eboInds[eboIndsEnd.current] = newEboInds[0]
        eboInds[eboIndsEnd.peekNext()] = newEboInds[1]

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo)
//        glBufferData(
//            GL_ELEMENT_ARRAY_BUFFER,
//            Loader.storeDataInIntBuffer(eboInds.toIntArray()),
//            GL_DYNAMIC_DRAW
//        )
        glBufferSubData(
            GL_ELEMENT_ARRAY_BUFFER,
            eboIndsEnd.current.toLong() * 2 * 2,
            Loader.storeDataInIntBuffer(newEboInds.toIntArray())
        )
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)

        vtxEnd.incrementByOne()
        eboIndsEnd.incrementBy(2)
    }

}