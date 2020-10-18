package no.sigurof.grajuny.resource.mesh

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray
import org.lwjgl.opengl.ARBVertexArrayObject.glDeleteVertexArrays
import org.lwjgl.opengl.ARBVertexArrayObject.glGenVertexArrays
import org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER
import org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER
import org.lwjgl.opengl.GL15.GL_FLOAT
import org.lwjgl.opengl.GL15.GL_STATIC_DRAW
import org.lwjgl.opengl.GL15.glBindBuffer
import org.lwjgl.opengl.GL15.glBufferData
import org.lwjgl.opengl.GL15.glDeleteBuffers
import org.lwjgl.opengl.GL15.glGenBuffers
import org.lwjgl.opengl.GL20.glVertexAttribPointer
import java.nio.FloatBuffer
import java.nio.IntBuffer

class MeshInfo(
    val vao: Int,
    val numberOfIndices: Int,
    val attributes: List<Int>
)

object Loader {

    private val vaos = ArrayList<Int>()
    private val vbos = ArrayList<Int>()

    fun loadToVao(parsedMesh: ParsedMesh): Mesh {
        val indices = parsedMesh.eboIndices.toIntArray()
        val vao = createVao()
        glBindVertexArray(vao)
        bindIndicesBuffer(indices, GL_STATIC_DRAW)
        val attributes = listOf(0, 1, 2)
        storeDataInAttributeList(
            attributes[0],
            3,
            parsedMesh.vertexCoordinates.toFloatArray()
        )
        storeDataInAttributeList(
            attributes[1],
            2,
            parsedMesh.uvCoordinates.toFloatArray()
        )
        storeDataInAttributeList(
            attributes[2],
            3,
            parsedMesh.normalVectors.toFloatArray()
        )
        unbindVao()
        return Mesh(
            vao = vao,
            vertexCount = indices.size,
            attributes = attributes
        )
    }

    fun cleanUp() {
        for (vao in vaos) {
            glDeleteVertexArrays(vao)
        }

        for (vbo in vbos) {
            glDeleteBuffers(vbo)
        }
    }

    internal fun createVao(): Int {
        val vao = glGenVertexArrays()
        vaos.add(vao)
        return vao
    }

    fun storeDataInAttributeList(
        attributeNumber: Int,
        coordinateSize: Int,
        data: FloatArray,
        usage: Int = GL_STATIC_DRAW
    ): Int {
        val vbo = glGenBuffers()
        vbos.add(vbo)
        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        val buffer = storeDataInFloatBuffer(data)
        glBufferData(GL_ARRAY_BUFFER, buffer, usage)
        glVertexAttribPointer(attributeNumber, coordinateSize, GL_FLOAT, false, 0, 0)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
        return vbo
    }

    private fun unbindVao() {
        glBindVertexArray(0)
    }

    fun bindIndicesBuffer(indices: IntArray, usage: Int): Int {
        val ebo = glGenBuffers()
        vbos.add(ebo)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo)
        val buffer = storeDataInIntBuffer(indices)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, usage)
        return ebo
    }

    fun storeDataInIntBuffer(data: IntArray): IntBuffer {
        val buffer = BufferUtils.createIntBuffer(data.size)
        buffer.put(data)
        buffer.flip()
        return buffer
    }

    fun storeDataInFloatBuffer(data: FloatArray): FloatBuffer {
        val buffer: FloatBuffer = BufferUtils.createFloatBuffer(data.size)
        buffer.put(data)
        buffer.flip()
        return buffer
    }

}
