package no.sigurof.grajuny.restructuring.shader.interfaces

import no.sigurof.grajuny.restructuring.LightSource

interface LightShader {

    fun loadLight(lightSource: LightSource)
}