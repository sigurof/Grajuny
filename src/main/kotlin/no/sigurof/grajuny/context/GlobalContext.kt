package no.sigurof.grajuny.context

import no.sigurof.grajuny.shaders.settings.ShaderSettings

interface GlobalContext<S: ShaderSettings> {
    fun loadUniforms(shader: S)
}