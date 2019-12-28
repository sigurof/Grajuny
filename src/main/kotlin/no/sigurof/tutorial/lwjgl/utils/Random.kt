package no.sigurof.tutorial.lwjgl.utils

import no.sigurof.tutorial.lwjgl.entity.Entity
import no.sigurof.tutorial.lwjgl.model.TexturedModel
import org.joml.Vector3f
import kotlin.random.Random


fun randomEntity(texturedModel: TexturedModel, dist: Float = 100f): Entity {
    val pos = randomDirection().mul(randomFloatBetween(0f, dist))
    val angles = randomEulerAngles()
    val r = randomFloatBetween(0.5f, 3f)
    val scale = Vector3f(r, r, r)
    return Entity(texturedModel, pos, angles, scale)
}


fun randomDirection(): Vector3f {
    return Vector3f(
        Random.nextFloat() * 2f - 1f,
        Random.nextFloat() * 2f - 1f,
        Random.nextFloat() * 2f - 1f
    ).normalize()
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