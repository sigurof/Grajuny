package no.sigurof.grajuny.restructuring

import org.joml.Vector3f

class TMaterial(
    val color: Vector3f,
    val reflectivity: Float,
    val shineDamper: Float
) {
    fun render(shader: BasicShader) {
        shader.loadColor(color)
        shader.loadSpecularValues(shineDamper, reflectivity)
    }

}