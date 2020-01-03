package no.sigurof.tutorial.lwjgl.context

import no.sigurof.tutorial.lwjgl.shaders.settings.ShaderSettings

interface GlobalContext<S : ShaderSettings> {
    fun loadUniforms(shader: S)
}