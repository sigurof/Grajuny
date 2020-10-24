package no.sigurof.grajuny.components

import no.sigurof.grajuny.node.GameComponent
import no.sigurof.grajuny.node.GameObject
import no.sigurof.grajuny.rendable.Rendable
import no.sigurof.grajuny.resource.TraceResource
import no.sigurof.grajuny.shader.Shader
import no.sigurof.grajuny.shader.shaders.LineShader
import no.sigurof.grajuny.utils.ORIGIN
import org.joml.Matrix4f
import org.joml.Vector3f

class TraceRenderer private constructor(
    private val color: Vector3f,
    private val numberOfPoints: Int,
    points: MutableList<Vector3f>,
    override var parent: GameObject?
) : GameComponent, Rendable(
    shadersToUse = listOf(LineShader)
) {
    private val trace = TraceResource(points)

    data class Builder(
        private val color: Vector3f,
        private val numberOfPoints: Int = 10,
        private var firstPos: Vector3f = ORIGIN
    ) {
        private var parent: GameObject? = null
        fun attachTo(value: GameObject) = apply { parent = value }
        fun firstPos(value: Vector3f) = apply { firstPos = value }
        fun build(): TraceRenderer {
            val trace = TraceRenderer(
                color = color,
                numberOfPoints = numberOfPoints,
                parent = parent ?: error("Parent not set!"),
                points = (0 until numberOfPoints).map { firstPos }.toMutableList()
            )
            parent?.addGameComponent(trace as GameComponent)
            return trace
        }
    }

    override fun input() {

    }

    override fun onRender(shader: Shader, transform: Matrix4f) {
        if (shader is LineShader) {
            shader.loadTransformationMatrix(Matrix4f())
            shader.loadColor(color)
            trace.addPoint(transform.getColumn(3, Vector3f()))
            trace.activate()
            trace.render()
            trace.deactivate()
        }
    }

    override fun update() {

    }

}