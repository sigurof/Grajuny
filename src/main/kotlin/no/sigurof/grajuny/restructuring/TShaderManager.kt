package no.sigurof.grajuny.restructuring

import no.sigurof.grajuny.restructuring.shader.Shader

object TShaderManager {

    private val activeShaders: MutableSet<Shader> = mutableSetOf()

    fun addShader(shader: Shader) {
        this.activeShaders.add(shader)
    }

    fun addShaders(shadersToUse: List<Shader>) {
        this.activeShaders.addAll(shadersToUse)
    }

    fun cleanUp() {
        activeShaders.forEach { it.cleanUp() }
    }

}