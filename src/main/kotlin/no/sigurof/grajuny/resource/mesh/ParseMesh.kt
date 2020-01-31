package no.sigurof.grajuny.resource.mesh

import org.joml.Vector2f
import org.joml.Vector3f
import org.joml.Vector3i

data class ParsedMesh(
    val eboIndices: List<Int> = mutableListOf(),
    val vertexCoordinates: List<Float> = mutableListOf(),
    val uvCoordinates: List<Float> = mutableListOf(),
    val normalVectors: List<Float> = mutableListOf()
) {
    companion object {
        fun from(source: String): ParsedMesh {
            return parseObjFile(source)
        }
    }
}

private val keywords = listOf("v", "vt", "vn", "f")
fun parseObjFile(source: String): ParsedMesh {
    // Map containing number of lines which start with each keyword
    val lineIndsByKeyword: Map<String, MutableList<Int>> = keywords
        .map { it to mutableListOf<Int>() }
        .toMap()

    val lines = ParsedMesh::class.java.getResource(source).file.lines()
    for ((i, line) in lines.withIndex()) {
        parseKeyword(line)?.let {
            lineIndsByKeyword.getValue(it).add(i)
        }
    }
    // Constructing arrays over all  unique vertices, uvs and normals
    val numVtxCoords = lineIndsByKeyword.getValue("v").size
    val numUvCoords = lineIndsByKeyword.getValue("vt").size
    val numNormCoords = lineIndsByKeyword.getValue("vn").size
    val vtxCoords = FloatArray(3 * numVtxCoords) { 0f }
    val uvCoords = FloatArray(2 * numUvCoords) { 0f }
    val normCoords = FloatArray(3 * numNormCoords) { 0f }

    for ((i, index) in lineIndsByKeyword.getValue("v").withIndex()) {
        parseVector3f(lines[index]).let {
            vtxCoords[3 * i + 0] = it.x
            vtxCoords[3 * i + 1] = it.y
            vtxCoords[3 * i + 2] = it.z
        }
    }
    for ((i, index) in lineIndsByKeyword.getValue("vt").withIndex()) {
        parseVector2f(lines[index]).let {
            uvCoords[2 * i + 0] = it.x
            uvCoords[2 * i + 1] = it.y
        }
    }
    for ((i, index) in lineIndsByKeyword.getValue("vn").withIndex()) {
        parseVector3f(lines[index]).let {
            normCoords[3 * i + 0] = it.x
            normCoords[3 * i + 1] = it.y
            normCoords[3 * i + 2] = it.z
        }
    }

    // Constructing fullsize vertex, normal and uv coordinate arrays.
    // In the file it's 2D (N times 3 faces). Here I use a flatMap to
    // reduce it to a 1D List<Vector3i>
    val vertices: List<Vector3i> = lineIndsByKeyword.getValue("f")
        .map { lines[it] }
        .flatMap { parseFaceVertices(it) }

    val indices = mutableListOf<Int>()
    val newVtxCoords = mutableListOf<Float>()
    val newUvCoords = mutableListOf<Float>()
    val newNormCoords = mutableListOf<Float>()
    val indexWhereVertexOccuredBefore = mutableMapOf<Vector3i, Int>()
    var i = 0
    for (vertex in vertices) {
        indexWhereVertexOccuredBefore[vertex]?.let {
            indices.add(it)
        } ?: run {
            indexWhereVertexOccuredBefore.put(vertex, i)
            newVtxCoords.add(vtxCoords[3 * vertex.x + 0])
            newVtxCoords.add(vtxCoords[3 * vertex.x + 1])
            newVtxCoords.add(vtxCoords[3 * vertex.x + 2])
            newUvCoords.add(uvCoords[2 * vertex.y + 0])
            newUvCoords.add(uvCoords[2 * vertex.y + 1])
            newNormCoords.add(normCoords[3 * vertex.z + 0])
            newNormCoords.add(normCoords[3 * vertex.z + 1])
            newNormCoords.add(normCoords[3 * vertex.z + 2])
            indices.add(i)
            i += 1
        }
    }
    return ParsedMesh(
        indices,
        newVtxCoords,
        newUvCoords,
        newNormCoords
    )
}

private fun parseKeyword(line: String): String? {
    return keywords
        .filter { line.startsWith(it, true) }
        .maxBy { it.length }
}

private fun parseVector3f(line: String): Vector3f {
    val parts = line.split(' ')
        .mapNotNull { it.toFloatOrNull() }
    assert(parts.size == 3)
    return Vector3f(parts[0], parts[1], parts[2])
}

private fun parseVector2f(line: String): Vector2f {
    val parts = line.split(' ')
        .mapNotNull { it.toFloatOrNull() }
    assert(parts.size == 2)
    return Vector2f(parts[0], parts[1])
}

// e.g. parse "101/21/1  14/12/13  100/21/1""
private fun parseFaceVertices(line: String): List<Vector3i> {
    return line.split(' ')
        .mapNotNull { parseVertexOrNull(it) }
}

// e.g. parse "12/13/99"
private fun parseVertexOrNull(line: String): Vector3i? {
    val integers = line
        .split("/")
        .mapNotNull { it.toIntOrNull() }
    if (integers.size == 3) {
        return Vector3i(integers[0] - 1, integers[1] - 1, integers[2] - 1)
    }
    return null
}
