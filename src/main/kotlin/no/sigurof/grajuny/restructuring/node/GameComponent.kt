package no.sigurof.grajuny.restructuring.node

import no.sigurof.grajuny.restructuring.engine.RenderingEngine
import no.sigurof.grajuny.restructuring.shader.Shader
import org.joml.Matrix4f

abstract class GameComponent(
    shadersToUse: List<Shader>
) {

    init {
        RenderingEngine.shaders.addAll(shadersToUse)
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
