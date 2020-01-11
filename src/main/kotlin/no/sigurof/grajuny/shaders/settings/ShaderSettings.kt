package no.sigurof.grajuny.shaders.settings

import no.sigurof.grajuny.resource.ResourceGl

interface ShaderSettings {
    fun usingVaoDo(resource: ResourceGl<*>, function: () -> Unit)
    fun cleanUp()
}