package no.sigurof.tutorial.lwjgl.model

import no.sigurof.tutorial.lwjgl.context.DefaultSceneContext

interface Model {
    fun render(globalContext: DefaultSceneContext)
    fun cleanShader()
}
