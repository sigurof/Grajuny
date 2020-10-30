package no.sigurof.grajuny.light

import no.sigurof.grajuny.shader.Shader

interface Bulb {

    fun render(shader: Shader)
}