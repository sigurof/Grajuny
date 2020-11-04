package no.sigurof.grajuny.shader.interfaces

import no.sigurof.grajuny.light.Light

interface LightShader{
    fun render(lights: MutableList<Light>)
}

