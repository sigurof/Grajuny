package no.sigurof.tutorial.lwjgl.shaders

class StaticShader : ShaderProgram(vtxSource, frgSource) {

    companion object {
        val vtxSource = "src/main/resources/shader/static/vertex.shader"
        val frgSource = "src/main/resources/shader/static/fragment.shader"
    }

    public override fun bindAttributes() {
        bindAttribute(0, "position")
    }

}