package no.sigurof.tutorial.lwjgl.shaders.settings

import no.sigurof.tutorial.lwjgl.resource.ResourceGl

interface ShaderSettings{
    fun usingVaoDo(resource: ResourceGl, function: ()->Unit)
    fun cleanUp()
}