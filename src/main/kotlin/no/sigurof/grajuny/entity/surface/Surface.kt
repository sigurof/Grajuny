package no.sigurof.grajuny.entity.surface

import no.sigurof.grajuny.shaders.settings.ShaderSettings

interface Surface<T : ShaderSettings> {
    fun loadUniforms(shader: T)
}