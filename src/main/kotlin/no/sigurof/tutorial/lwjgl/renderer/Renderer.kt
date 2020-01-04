package no.sigurof.tutorial.lwjgl.renderer

import no.sigurof.tutorial.lwjgl.context.GlobalContext

interface Renderer {
    fun render(globalContext: GlobalContext)
    fun cleanShader()
}
