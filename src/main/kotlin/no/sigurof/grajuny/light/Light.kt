package no.sigurof.grajuny.light

import no.sigurof.grajuny.shader.Shader

interface Light {

    fun render(shader: Shader)
}