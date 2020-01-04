package no.sigurof.tutorial.lwjgl.resource

import no.sigurof.tutorial.lwjgl.shaders.settings.ShaderSettings

interface ResourceGl<in S: ShaderSettings> {
    fun render()
    fun prepare(shader: S)
    fun getVao() : Int
}