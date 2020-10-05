package no.sigurof.grajuny.restructuring

import org.joml.Matrix4f

abstract class GameComponent {

    abstract var transform: Matrix4f
    abstract fun input()
    abstract fun upload(shader: Shader, transform: Matrix4f)
    fun render(shader: Shader, parentTransform: Matrix4f) {
        val compositeTransform = parentTransform.mul(transform, Matrix4f())
        upload(shader, compositeTransform)
    }

    abstract fun update()

}
