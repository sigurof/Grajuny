package no.sigurof.grajuny.resource

import no.sigurof.grajuny.shaders.settings.ShaderSettings

interface ResourceGl<in S: ShaderSettings> {
    val vao: Int
    fun render()
    fun activate(shader: S)
    fun deactivate(shader: S)
}