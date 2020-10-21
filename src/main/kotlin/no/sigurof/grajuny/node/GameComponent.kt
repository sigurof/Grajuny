package no.sigurof.grajuny.node

import no.sigurof.grajuny.shader.Shader
import no.sigurof.grajuny.shader.ShaderManager
import org.joml.Matrix4f

abstract class GameComponent(
    var parent: GameObject? = null,
    val shadersToUse: List<Shader>
) {

    init {
        ShaderManager.activeShaders.addAll(shadersToUse)
    }

    abstract var transform: Matrix4f
    abstract fun input()
    abstract fun upload(shader: Shader, transform: Matrix4f)
    fun render(shader: Shader, parentTransform: Matrix4f) {
        val compositeTransform = parentTransform.mul(transform, Matrix4f())
        upload(shader, compositeTransform)
    }

    abstract fun update()

}
