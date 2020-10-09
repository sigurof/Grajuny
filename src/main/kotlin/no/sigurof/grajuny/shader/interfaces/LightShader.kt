package no.sigurof.grajuny.shader.interfaces

import no.sigurof.grajuny.light.LightSource

interface LightShader {

    fun loadLight(lightSource: LightSource)
}