package no.sigurof.grajuny.components

import no.sigurof.grajuny.node.GameComponent
import no.sigurof.grajuny.node.GameObject
import no.sigurof.grajuny.rendable.Rendable
import no.sigurof.grajuny.resource.LineResource
import no.sigurof.grajuny.shader.Shader
import no.sigurof.grajuny.shader.shaders.LineShader
import org.joml.Matrix4f
import org.joml.Vector3f

class LineRenderer(
    private val line: LineResource,
    private val color: Vector3f
) : GameComponent, Rendable(
    shadersToUse = listOf(LineShader)
) {
    override var parent: GameObject? = null

    override fun input() {

    }

    override fun onRender(shader: Shader, transform: Matrix4f) {
        if (shader is LineShader) {
            shader.loadTransformationMatrix(transform)
            shader.loadColor(color)
            line.activate()
            line.render()
            line.deactivate()
        }
    }

    override fun update() {
    }
}