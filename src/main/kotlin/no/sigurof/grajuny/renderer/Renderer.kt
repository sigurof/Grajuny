package no.sigurof.grajuny.renderer

import no.sigurof.grajuny.context.GlobalContext

interface Renderer {
    fun render(globalContext: GlobalContext)
    fun cleanShader()
}
