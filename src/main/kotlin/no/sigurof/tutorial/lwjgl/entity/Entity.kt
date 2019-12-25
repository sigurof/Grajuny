package no.sigurof.tutorial.lwjgl.entity

import no.sigurof.tutorial.lwjgl.model.TexturedModel
import org.joml.Vector3f

class Entity constructor(
    public val texturedModel: TexturedModel,
    var position: Vector3f,
    val eulerAngles: Vector3f,
    val scale: Vector3f
) {
    public fun increasePosition(increment: Vector3f) {
        position.add(increment)
    }

    public fun increaseRotation(increment: Vector3f) {
        eulerAngles.add(increment)
    }

    public fun increaseScale(increment: Vector3f) {
        scale.add(increment)
    }
}