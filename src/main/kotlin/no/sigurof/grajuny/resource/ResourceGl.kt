package no.sigurof.grajuny.resource

interface ResourceGl {
    val vao: Int
    fun render()
    fun activate()
    fun deactivate()
}