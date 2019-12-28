package no.sigurof.tutorial.lwjgl.entity

import org.joml.Vector3f

class Light private constructor(
    var position: Vector3f,
    var color: Vector3f,
    var ambient: Float
) {
    data class With(
        var position: Vector3f = Vector3f(0f, 0f, 0f),
        var color: Vector3f = Vector3f(1f, 1f, 1f),
        var ambient: Float = 0f
    ) {
        fun position(position: Vector3f) = apply { this.position = position }
        fun color(color: Vector3f) = apply { this.color = color }
        fun ambient(ambient: Float) = apply { this.ambient = ambient }
        fun build(): Light {
            return Light(position, color, ambient)
        }
    }
}