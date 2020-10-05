package no.sigurof.grajuny.restructuring

object TShaderManager {

    private val activeShaders: MutableList<Shader> = mutableListOf()

    fun addShader(shader: Shader) {
        this.activeShaders.add(shader)
    }

    fun cleanUp() {
        activeShaders.forEach { it.cleanUp() }
    }

}