package no.sigurof.tutorial.lwjgl.engine

import de.matthiasmann.twl.utils.PNGDecoder
import no.sigurof.tutorial.lwjgl.mesh.Vao
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray
import org.lwjgl.opengl.ARBVertexArrayObject.glDeleteVertexArrays
import org.lwjgl.opengl.ARBVertexArrayObject.glGenVertexArrays
import org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER
import org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER
import org.lwjgl.opengl.GL15.GL_FLOAT
import org.lwjgl.opengl.GL15.GL_NEAREST
import org.lwjgl.opengl.GL15.GL_RGB
import org.lwjgl.opengl.GL15.GL_STATIC_DRAW
import org.lwjgl.opengl.GL15.GL_TEXTURE_2D
import org.lwjgl.opengl.GL15.GL_TEXTURE_MAG_FILTER
import org.lwjgl.opengl.GL15.GL_TEXTURE_MIN_FILTER
import org.lwjgl.opengl.GL15.GL_UNSIGNED_BYTE
import org.lwjgl.opengl.GL15.glBindBuffer
import org.lwjgl.opengl.GL15.glBindTexture
import org.lwjgl.opengl.GL15.glBufferData
import org.lwjgl.opengl.GL15.glDeleteBuffers
import org.lwjgl.opengl.GL15.glDeleteTextures
import org.lwjgl.opengl.GL15.glGenBuffers
import org.lwjgl.opengl.GL15.glGenTextures
import org.lwjgl.opengl.GL15.glTexImage2D
import org.lwjgl.opengl.GL15.glTexParameteri
import org.lwjgl.opengl.GL20.glVertexAttribPointer
import org.lwjgl.opengl.GL30.glGenerateMipmap
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.FloatBuffer
import java.nio.IntBuffer

object Loader {

    private val vaos = ArrayList<Int>()
    private val vbos = ArrayList<Int>()
    private val textures = ArrayList<Int>()

    fun loadToVao(positions: FloatArray, uvs: FloatArray, indices: IntArray, normals: FloatArray): Vao {
        val vao = createVao()
        glBindVertexArray(vao)
        bindIndicesBuffer(indices)
        storeDataInAttributeList(0, 3, positions)
        storeDataInAttributeList(1, 2, uvs)
        storeDataInAttributeList(2, 3, normals)
        unbindVao()
        return Vao(vao, indices.size)
    }

    fun loadTexture(filePath: String): Int {
        val inputStream = FileInputStream(filePath)
        val decoder = PNGDecoder(inputStream)
        val buffer: ByteBuffer = ByteBuffer.allocateDirect(3 * decoder.width * decoder.height)
        decoder.decode(buffer, decoder.width * 3, PNGDecoder.Format.RGB)
        buffer.flip()
        inputStream.close()

        val texture = glGenTextures()
        textures.add(texture)
        glBindTexture(GL_TEXTURE_2D, texture)
        glTexImage2D(
            GL_TEXTURE_2D, 0, GL_RGB, decoder.width, decoder.height, 0,
            GL_RGB, GL_UNSIGNED_BYTE, buffer
        )
        glGenerateMipmap(GL_TEXTURE_2D)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)
        return texture
    }

    fun cleanUp() {
        for (vao in vaos) {
            glDeleteVertexArrays(vao)
        }

        for (vbo in vbos) {
            glDeleteBuffers(vbo)
        }
        for (tex in textures) {
            glDeleteTextures(tex)
        }
    }

    internal fun createVao(): Int {
        val vao = glGenVertexArrays()
        vaos.add(vao)
        return vao
    }

    private fun storeDataInAttributeList(attributeNumber: Int, coordinateSize: Int, data: FloatArray) {
        val vbo = glGenBuffers()
        vbos.add(vbo)
        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        val buffer = storeDataInFloatBuffer(data)
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW)
        glVertexAttribPointer(attributeNumber, coordinateSize, GL_FLOAT, false, 0, 0)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
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
