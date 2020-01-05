package no.sigurof.tutorial.lwjgl.resource

import no.sigurof.tutorial.lwjgl.shaders.settings.ShaderSettings

interface ResourceGl<in S: ShaderSettings> {
    fun render()
    fun using(shader: S, function: ()->Unit)
    fun getVao() : Int
}