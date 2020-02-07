package no.sigurof.grajuny.experimental

interface ShaderInterface<G> {
    fun render(context: G)
    fun cleanShader()
}