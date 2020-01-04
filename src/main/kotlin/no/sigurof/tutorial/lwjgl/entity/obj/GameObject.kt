package no.sigurof.tutorial.lwjgl.entity.obj

import no.sigurof.tutorial.lwjgl.shaders.settings.ShaderSettings

interface GameObject<in S: ShaderSettings> {
    fun loadUniforms(shader: S)
}