package no.sigurof.grajuny.node

import no.sigurof.grajuny.shader.Shader
import org.joml.Matrix4f

interface GameComponent {

    var parent: GameObject?
    val shadersToUse: List<Shader>

    fun input()

    fun render(shader: Shader, transform: Matrix4f)

    fun update()

}
