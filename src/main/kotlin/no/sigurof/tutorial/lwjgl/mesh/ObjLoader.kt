package no.sigurof.tutorial.lwjgl.mesh

import no.sigurof.tutorial.lwjgl.engine.Loader
import no.sigurof.tutorial.lwjgl.model.RawModel
import org.joml.Vector2f
import org.joml.Vector3f
import org.joml.Vector3i
import org.joml.Vector4i
import java.io.File

class ObjLoader {

    companion object {
        val keywords = listOf("v", "vt", "vn", "f")
    }

    data class UvAndNormalIndices(
        val uvIndex: Int,
        val normalIndex: Int
    )

    fun loadObjModel(source: String): RawModel {
        // Map containing number of lines which start with each keyword
        val lineIndsByKeyword: Map<String, MutableList<Int>> = keywords
            .map { it to mutableListOf<Int>() }
            .toMap()

        val lines = File(source).readLines()
        for ((i, line) in lines.withIndex()) {
            parseKeyword(line)?.let {
                lineIndsByKeyword[it]!!.add(i)
            }
        }
        // Constructing arrays over all  unique vertices, uvs and normals
        val numVtxCoords = lineIndsByKeyword["v"]!!.size
        val numUvCoords = lineIndsByKeyword["vt"]!!.size
        val numNormCoords = lineIndsByKeyword["vn"]!!.size
        val vtxCoords = FloatArray(3 * numVtxCoords) { 0f }
        val uvCoords = FloatArray(2 * numUvCoords) { 0f }
        val normCoords = FloatArray(3 * numNormCoords) { 0f }

        for ((i, index) in lineIndsByKeyword["v"]!!.withIndex()) {
            parseVector3f(lines[index]).let {
                vtxCoords[3 * i + 0] = it.x
                vtxCoords[3 * i + 1] = it.y
                vtxCoords[3 * i + 2] = it.z
            }
        }
        for ((i, index) in lineIndsByKeyword["vt"]!!.withIndex()) {
            parseVector2f(lines[index]).let {
                uvCoords[2 * i + 0] = it.x
                uvCoords[2 * i + 1] = it.y
            }
        }
        for ((i, index) in lineIndsByKeyword["vn"]!!.withIndex()) {
            parseVector3f(lines[index]).let {
                normCoords[3 * i + 0] = it.x
                normCoords[3 * i + 1] = it.y
                normCoords[3 * i + 2] = it.z
            }
        }

        // Constructing the ebo list over indices and
        // constructing a map of each vertex to the corresponding uv-index and normal-index
        val vertices: List<Vector3i> = lineIndsByKeyword["f"]!!
            .map { lines[it] }
            .flatMap { parseFaceVertices(it) }

        val indices = vertices.indices.toList().toIntArray();
        val newVtxCoords = vertices
            .flatMap {
                listOf(
                    vtxCoords[3 * it.x + 0],
                    vtxCoords[3 * it.x + 1],
                    vtxCoords[3 * it.x + 2]
                )
            }
            .toFloatArray()
        val newUvCoords = vertices
            .flatMap {
                listOf(
                    uvCoords[2 * it.y + 0],
                    uvCoords[2 * it.y + 1]
                    )
            }
            .toFloatArray()
        val newNormCoords = vertices
            .flatMap {
                listOf(
                    normCoords[3 * it.z + 0],
                    normCoords[3 * it.z + 1],
                    normCoords[3 * it.z + 2]
                )
            }
            .toFloatArray()

        return Loader.loadToVao(newVtxCoords, newUvCoords, indices, newNormCoords)
    }


    private fun parseKeyword(line: String): String? {
        return keywords
            .filter { line.startsWith(it, true) }
            .maxBy { it.length }
    }


    private fun parseVector3f(line: String): Vector3f {
        val parts = line.split(' ')
            .map { it.toFloatOrNull() }
            .filterNotNull()
        assert(parts.size == 3)
        return Vector3f(parts[0], parts[1], parts[2])
    }

    private fun parseVector2f(line: String): Vector2f {
        val parts = line.split(' ')
            .map { it.toFloatOrNull() }
            .filterNotNull()
        assert(parts.size == 2)
        return Vector2f(parts[0], parts[1])
    }


    private fun parseFaceVertices(line: String): List<Vector3i> {
        return line.split(' ')
            .map { parseVertexOrNull(it) }
            .filterNotNull()
    }

    private fun parseVertexOrNull(line: String): Vector3i? {
        val ints = line
            .split("/")
            .map { it.toIntOrNull() }
            .filterNotNull()
        if (ints.size == 3) {
            return Vector3i(ints[0]-1, ints[1]-1, ints[2]-1)
        }
        return null
    }

    private fun parseFace(line: String): IntArray {
        return line.split(' ', '/')
            .map { it.toIntOrNull() }
            .filterNotNull()
            .map { it - 1 }
            .toIntArray()
    }

}