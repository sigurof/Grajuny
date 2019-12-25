package no.sigurof.tutorial.lwjgl.engine

import de.matthiasmann.twl.utils.PNGDecoder
import no.sigurof.tutorial.lwjgl.model.RawModel
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.ARBVertexArrayObject.*
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.glVertexAttribPointer
import org.lwjgl.opengl.GL30.glGenerateMipmap
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.FloatBuffer
import java.nio.IntBuffer


class Loader {
    companion object {


        private val vaos = ArrayList<Int>()
        private val vbos = ArrayList<Int>()
        private val textures = ArrayList<Int>()

        fun loadToVao(positions: FloatArray, uvs: FloatArray, indices: IntArray, normals: FloatArray): RawModel {
            val vao = createVao()
            bindIndicesBuffer(indices)
            storeDataInAttributeList(0, 3, positions)
            storeDataInAttributeList(1, 2, uvs)
            storeDataInAttributeList(2, 3, normals)
            unbindVao()
            return RawModel(vao, indices.size)
        }


        // Kan brukes for å fjerne det ekstra oversettelsessteget for å oversette fra array til buffer
        fun loadBuffersToVao(positions: FloatBuffer, uvs: FloatBuffer, indices: IntBuffer): RawModel {
            val vao = createVao()
            // Indices
            val ebo = glGenBuffers()
            vbos.add(ebo)
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo)
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW)

            // Positions
            val vbo = glGenBuffers()
            vbos.add(vbo)
            glBindBuffer(GL_ARRAY_BUFFER, vbo)
            glBufferData(GL_ARRAY_BUFFER, positions, GL_STATIC_DRAW)
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0)
            glBindBuffer(GL_ARRAY_BUFFER, 0)

            // Texture
            val vbo2 = glGenBuffers()
            vbos.add(vbo2)
            glBindBuffer(GL_ARRAY_BUFFER, vbo2)
            glBufferData(GL_ARRAY_BUFFER, uvs, GL_STATIC_DRAW)
            glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0)
            glBindBuffer(GL_ARRAY_BUFFER, 0)
            unbindVao()
            return RawModel(vao, indices.capacity())
        }


        fun loadToVao(positions: FloatArray, indices: IntArray): RawModel {
            val vao = createVao()
            bindIndicesBuffer(indices)
            storeDataInAttributeList(0, 3, positions)
            unbindVao()
            return RawModel(vao, indices.size)
        }

        fun loadTexture(filePath: String): Int {
            val inputStream = FileInputStream(filePath)
            val decoder = PNGDecoder(inputStream)
            val buffer: ByteBuffer = ByteBuffer.allocateDirect(3 * decoder.getWidth() * decoder.getHeight())
            decoder.decode(buffer, decoder.getWidth() * 3, PNGDecoder.Format.RGB)
            buffer.flip()
            inputStream.close()

            val texture = glGenTextures()
            textures.add(texture)
            glBindTexture(GL_TEXTURE_2D, texture)
            glTexImage2D(
                GL_TEXTURE_2D, 0, GL_RGB, decoder.getWidth(), decoder.getHeight(), 0,
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

        private fun createVao(): Int {
            val vao = glGenVertexArrays()
            vaos.add(vao)
            glBindVertexArray(vao)
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
}
