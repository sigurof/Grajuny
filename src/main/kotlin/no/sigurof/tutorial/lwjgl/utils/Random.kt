package no.sigurof.tutorial.lwjgl.utils

import org.joml.Vector3d
import org.joml.Vector3f
import kotlin.random.Random


fun randomVector3f(): Vector3f {
    return Vector3f(
        Random.nextFloat(),
        Random.nextFloat(),
        Random.nextFloat()
    )
}