package no.sigurof.tutorial.lwjgl.renderer

import no.sigurof.tutorial.lwjgl.context.DefaultSceneContext

interface Renderer {
    fun render(globalContext: DefaultSceneContext)
    fun cleanShader()
}
