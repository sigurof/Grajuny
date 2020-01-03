package no.sigurof.tutorial.lwjgl.entity.surface

import no.sigurof.tutorial.lwjgl.shaders.settings.ShaderSettings

interface Surface<T : ShaderSettings> {
    fun loadUniforms(shader: T)
}