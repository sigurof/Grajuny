package no.sigurof.grajuny.light

import no.sigurof.grajuny.shader.interfaces.LightShader
import org.joml.Vector3f

class LightSource(
    var position: Vector3f,
    var color: Vector3f,
    var ambient: Float
) {
    fun render(shader: LightShader) {
        shader.loadLight(this)
    }

    data class Builder(
        var position: Vector3f = Vector3f(0f, 0f, 0f),
        var color: Vector3f = Vector3f(1f, 1f, 1f),
        var ambient: Float = 0.3f
    ) {
        fun position(position: Vector3f) = apply { this.position = position }
        fun color(color: Vector3f) = apply { this.color = color }
        fun ambient(ambient: Float) = apply { this.ambient = ambient }
        fun build(): LightSource {
            val tLight = LightSource(position, color, ambient)
            LIGHT_SOURCES.add(tLight)
            return tLight
        }
    }

    companion object {
        val LIGHT_SOURCES: MutableList<LightSource> = mutableListOf()
    }

}