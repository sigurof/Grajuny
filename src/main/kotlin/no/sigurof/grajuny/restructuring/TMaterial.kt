package no.sigurof.grajuny.restructuring

import no.sigurof.grajuny.restructuring.shader.interfaces.ColorSpecularShader
import org.joml.Vector3f

class TMaterial(
    val color: Vector3f,
    val reflectivity: Float,
    val shineDamper: Float
) {
    fun render(shader: ColorSpecularShader) {
        shader.loadColor(color)
        shader.loadSpecularValues(shineDamper, reflectivity)
    }

}