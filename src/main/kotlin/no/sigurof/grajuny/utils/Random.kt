package no.sigurof.grajuny.utils

import org.joml.Vector3f
import kotlin.random.Random


fun randomDirection(): Vector3f {
    return Vector3f(
        Random.nextFloat() * 2f - 1f,
        Random.nextFloat() * 2f - 1f,
        Random.nextFloat() * 2f - 1f
    ).normalize()
}

fun randomVector3f(min: Float, max: Float) : Vector3f{
    return Vector3f(
        randomFloatBetween(min, max),
        randomFloatBetween(min, max),
        randomFloatBetween(min, max)
    )
}

fun randomEulerAngles(): Vector3f {
    return Vector3f(
        randomFloatBetween(0f, 2f * 3.14159265f),
        randomFloatBetween(0f, 2f * 3.14159265f),
        randomFloatBetween(0f, 2f * 3.14159265f)
    )
}

fun randomFloatBetween(min: Float, max: Float): Float {
    return min + Random.nextFloat() * (max - min)
}

