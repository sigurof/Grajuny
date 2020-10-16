package no.sigurof.grajuny.components

import no.sigurof.grajuny.node.GameComponent
import no.sigurof.grajuny.resource.LineResource
import no.sigurof.grajuny.shader.Shader
import no.sigurof.grajuny.shader.shaders.LineShader
import org.joml.Matrix4f
import org.joml.Vector3f

class LineRenderer(
    private val line: LineResource,
    private val color: Vector3f
) : GameComponent(
    shadersToUse = listOf(LineShader)
) {
    override var transform: Matrix4f = Matrix4f().identity()

    override fun input() {

    }

    override fun upload(shader: Shader, transform: Matrix4f) {
        if (shader in shadersToUse) {
            if (shader is LineShader) {
                shader.loadTransformationMatrix(transform)
                shader.loadColor(color)
                line.activate()
                line.render()
                line.deactivate()
            }
        }
    }

    override fun update() {
    }
}