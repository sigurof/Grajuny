package no.sigurof.tutorial.lwjgl.model

import no.sigurof.tutorial.lwjgl.entity.Camera
import no.sigurof.tutorial.lwjgl.entity.Entity
import no.sigurof.tutorial.lwjgl.entity.Light

class ModelContainer(
    private val model: Model

) {

    fun render(
        entities: List<Entity>,
        light: Light,
        camera: Camera,
        fov: Float,
        nearPlane: Float,
        farPlane: Float
    ) {
        model.render(entities, light, camera, fov, nearPlane, farPlane)
    }

    fun cleanupShader(){
        model.cleanShader()
    }
}