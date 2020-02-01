package no.sigurof.grajuny.resource

import no.sigurof.grajuny.shaders.settings.ShaderSettings

interface ResourceGl<in S: ShaderSettings> {
    val vao: Int
    fun render()
    fun using(shader: S, function: ()->Unit)
}