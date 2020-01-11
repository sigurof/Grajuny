package no.sigurof.grajuny.entity.obj

import no.sigurof.grajuny.shaders.settings.ShaderSettings

interface GameObject<in S: ShaderSettings> {
    fun loadUniforms(shader: S)
}