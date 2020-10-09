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

object Loader {

    private val vaos = ArrayList<Int>()
    private val vbos = ArrayList<Int>()

    fun tmeshLoadToVao(parsedMesh: ParsedMesh): TMesh {
        return loadToVao(parsedMesh).let {
            TMesh(it.first, it.second)
        }
    }


    fun loadToVao(parsedMesh: ParsedMesh): Pair<Int, Int> {
        val indices = parsedMesh.eboIndices.toIntArray()
        val vao = createVao()
        glBindVertexArray(vao)
        bindIndicesBuffer(indices)
        storeDataInAttributeList(
            0,
            3,
            parsedMesh.vertexCoordinates.toFloatArray()
        )
        storeDataInAttributeList(
            1,
            2,
            parsedMesh.uvCoordinates.toFloatArray()
        )
        storeDataInAttributeList(
            2,
            3,
            parsedMesh.normalVectors.toFloatArray()
        )
        unbindVao()
        return Pair(vao, indices.size)
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

    private fun storeDataInAttributeList(attributeNumber: Int, coordinateSize: Int, data: FloatArray): Int {
        val vbo = glGenBuffers()
        vbos.add(vbo)
        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        val buffer = storeDataInFloatBuffer(data)
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW)
        glVertexAttribPointer(attributeNumber, coordinateSize, GL_FLOAT, false, 0, 0)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
        return vbo
    }

    private fun unbindVao() {
        glBindVertexArray(0)
    }

    private fun bindIndicesBuffer(indices: IntArray) {
        val ebo = glGenBuffers()
        vbos.add(ebo)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo)
        val buffer = storeDataInIntBuffer(indices)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW)
    }

    private fun storeDataInIntBuffer(data: IntArray): IntBuffer {
        val buffer = BufferUtils.createIntBuffer(data.size)
        buffer.put(data)
        buffer.flip()
        return buffer
    }

    private fun storeDataInFloatBuffer(data: FloatArray): FloatBuffer {
        val buffer: FloatBuffer = BufferUtils.createFloatBuffer(data.size)
        buffer.put(data)
        buffer.flip()
        return buffer
    }

}
