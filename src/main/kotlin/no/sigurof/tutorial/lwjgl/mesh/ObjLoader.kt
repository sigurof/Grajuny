package no.sigurof.tutorial.lwjgl.mesh

import no.sigurof.tutorial.lwjgl.engine.Loader
import no.sigurof.tutorial.lwjgl.model.RawModel
import org.joml.Vector2f
import org.joml.Vector3f
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
        val indices = mutableListOf<Int>()
        val uvAndNormIndsByVtxInds = HashMap<Int, UvAndNormalIndices>()
        vtxUvNorm@ for (index in lineIndsByKeyword["f"]!!) {
            val vtsUvsNormsForFace = parseFace(lines[index])
            for (j in 0..2) {
                if (uvAndNormIndsByVtxInds.size != numVtxCoords) {
                    uvAndNormIndsByVtxInds.put(
                        vtsUvsNormsForFace[3 * j],
                        UvAndNormalIndices(
                            vtsUvsNormsForFace[3 * j + 1],
                            vtsUvsNormsForFace[3 * j + 2]
                        )
                    )
                }
                indices.add(vtsUvsNormsForFace[3 * j])
            }
        }
        // Populating the new arrays of normals and uvs, where each element
        // corresponds to the element at the same index in the other two arrays,
        val newIndices = indices.toIntArray()
        val newUvCoords = FloatArray(2 * numVtxCoords) { 0f }
        val newNormCoords = FloatArray(3 * numVtxCoords) { 0f }
        for (entry in uvAndNormIndsByVtxInds) {
            newUvCoords[2 * entry.key + 0] = uvCoords[2 * entry.value.uvIndex + 0]
            newUvCoords[2 * entry.key + 1] = uvCoords[2 * entry.value.uvIndex + 1] * (-1f) + 1f // invert y
            newNormCoords[3 * entry.key + 0] = normCoords[3 * entry.value.normalIndex + 0]
            newNormCoords[3 * entry.key + 1] = normCoords[3 * entry.value.normalIndex + 1]
            newNormCoords[3 * entry.key + 2] = normCoords[3 * entry.value.normalIndex + 2]
        }
        return Loader.loadToVao(vtxCoords, newUvCoords, newIndices, newNormCoords)

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


    private fun parseFace(line: String): IntArray {
        return line.split(' ', '/')
            .map { it.toIntOrNull() }
            .filterNotNull()
            .map { it - 1 }
            .toIntArray()
    }

}