package no.sigurof.grajuny.context

import no.sigurof.grajuny.shaders.settings.ShaderSettings

interface GlobalContext {
    fun loadUniforms(shader: ShaderSettings)
}