package no.sigurof.grajuny.resource.material

import no.sigurof.grajuny.shader.Shader

interface Material {
    fun render(shader: Shader)
}