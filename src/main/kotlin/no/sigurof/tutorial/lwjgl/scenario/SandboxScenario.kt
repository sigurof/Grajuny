package no.sigurof.tutorial.lwjgl.scenario

import no.sigurof.tutorial.lwjgl.entity.Camera
import no.sigurof.tutorial.lwjgl.entity.Entity
import no.sigurof.tutorial.lwjgl.entity.Light
import no.sigurof.tutorial.lwjgl.model.TexturedModel
import no.sigurof.tutorial.lwjgl.shaders.TextureShader
import no.sigurof.tutorial.lwjgl.utils.randomEntity
import org.joml.Vector3f

class SandboxScenario private constructor(
    val shader: TextureShader,
    val camera: Camera,
    val light: Light,
    val entities: List<Entity>
) {
    companion object {
        fun default(window: Long): SandboxScenario {
            val texturedModel = TexturedModel.Builder()
                .withModel("sphere")
                .withTexture("default")
                .withReflectivity(1f)
                .withShineDamper(1f)
                .build()
            val light = Light.With()
                .position(Vector3f(0f, 10f, 5f))
                .color(Vector3f(1f, 1f, 1f))
                .ambient(0.1f)
                .build()
            val shader = TextureShader()
            val camera = Camera(window = window, position = Vector3f(0f, 0f, 0f))
            val entities = mutableListOf<Entity>()
            entities.add(Entity(texturedModel, Vector3f(0f, 0f, 10f), Vector3f(0f, 0f, 0f), Vector3f(1f, 1f, 1f)))
            for (i in 0..2) {
                entities.add(randomEntity(texturedModel, 1f))
            }
            return SandboxScenario(shader, camera, light, entities)
        }
    }
}