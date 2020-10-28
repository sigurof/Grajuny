package no.sigurof.grajuny.shader.shaders

import no.sigurof.grajuny.shader.Shader

object PostProcessingShader : Shader(
    vtxSource = "/shader/framebuffer/vertex.shader",
    frgSource = "/shader/framebuffer/fragment.shader",
    attributes = listOf(
        0 to "position",
        1 to "textureCoords"
    ),
    uniforms = listOf()
) {

}