package no.sigurof.tutorial.lwjgl.entity.obj

import no.sigurof.tutorial.lwjgl.shaders.settings.ShaderSettings

interface GameObject {
    fun loadUniforms(shader: ShaderSettings)
}