package no.sigurof.grajuny.rendable

import no.sigurof.grajuny.shader.Shader
import no.sigurof.grajuny.shader.ShaderManager
import org.joml.Matrix4f

abstract class Rendable(
    val shadersToUse: List<Shader>
) {
    init {
        ShaderManager.activeShaders.addAll(shadersToUse)
    }

    fun render(shader: Shader, transform: Matrix4f) {
        if (shader in shadersToUse) {
            onRender(shader, transform)
        }
    }

    abstract fun onRender(shader: Shader, transform: Matrix4f)
}
