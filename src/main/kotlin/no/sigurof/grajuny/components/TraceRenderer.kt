package no.sigurof.grajuny.components

import no.sigurof.grajuny.node.GameComponent
import no.sigurof.grajuny.node.GameObject
import no.sigurof.grajuny.resource.TraceResource
import no.sigurof.grajuny.shader.Shader
import no.sigurof.grajuny.shader.shaders.LineShader
import org.joml.Matrix4f
import org.joml.Vector3f

class TraceRenderer private constructor(
    private val color: Vector3f,
    private val numberOfPoints: Int,
    parent: GameObject
) : GameComponent(
    shadersToUse = listOf(LineShader),
    parent = parent
) {
    override var transform: Matrix4f = Matrix4f()

    private val points = (0 until numberOfPoints).map { i ->
        Vector3f(i.toFloat(), i.toFloat() * i.toFloat() * 0.05f, 0f)
    }.toMutableList()

    private val trace = TraceResource(points)

    data class Builder(
        private val color: Vector3f,
        private val numberOfPoints: Int = 10
    ) {
        private var parent: GameObject? = null
        fun attachTo(value: GameObject) = apply { parent = value }
        fun build(): TraceRenderer {
            val trace = TraceRenderer(
                color = color,
                numberOfPoints = numberOfPoints,
                parent = parent ?: error("Parent not set!")
            )
            parent?.addGameComponent(trace as GameComponent)
            return trace
        }
    }

    override fun input() {

    }

    override fun upload(shader: Shader, transform: Matrix4f) {
        if (shader in shadersToUse) {
            if (shader is LineShader) {
                shader.loadTransformationMatrix(Matrix4f())
                shader.loadColor(color)
                trace.addPoint(transform.getColumn(3, Vector3f()))
                trace.activate()
                trace.render()
                trace.deactivate()
            }
        }
    }

    override fun update() {

    }

}