package no.sigurof.grajuny.resource.mesh

data class Mesh(
    internal val vao: Int,
    internal val vertexCount: Int,
    internal val attributes: List<Int>
)
