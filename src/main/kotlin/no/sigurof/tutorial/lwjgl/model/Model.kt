package no.sigurof.tutorial.lwjgl.model

import no.sigurof.tutorial.lwjgl.entity.Camera
import no.sigurof.tutorial.lwjgl.entity.Entity
import no.sigurof.tutorial.lwjgl.entity.Light

interface Model {
    fun render(entities: List<Entity>, light: Light, camera: Camera, fov: Float, nearPlane: Float, farPlane: Float)
    fun cleanShader()
}