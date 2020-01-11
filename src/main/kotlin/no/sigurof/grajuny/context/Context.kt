package no.sigurof.grajuny.context

import no.sigurof.grajuny.shaders.settings.ShaderSettings

interface Context {
    fun loadUniforms(shader: ShaderSettings)
}