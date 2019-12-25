package no.sigurof.tutorial.lwjgl.engine

import no.sigurof.tutorial.lwjgl.entity.Camera
import no.sigurof.tutorial.lwjgl.entity.Entity
import no.sigurof.tutorial.lwjgl.entity.Light
import no.sigurof.tutorial.lwjgl.model.TexturedModel
import no.sigurof.tutorial.lwjgl.shaders.TextureShader

class MasterRenderer(
    private val shader: TextureShader = TextureShader(),
    private val camera: Camera,
    private val light: Light
) {
    private val renderer = Renderer(shader, camera, light)
    private val entities: HashMap<TexturedModel, ArrayList<Entity>> = hashMapOf()

    fun render() {
        renderer.prepare()
        shader.start()
        shader.loadLight(light)
        shader.loadViewMatrix(camera)
        renderer.render(entities)
        shader.stop()
        entities.clear()
    }

    fun processEntity(entity: Entity) {
        val entityModel = entity.texturedModel
        entities.get(entityModel)?.add(entity) ?: run {
            val newBatch = ArrayList<Entity>()
            newBatch.add(entity)
            entities.put(entityModel, newBatch)
        }

    }

    fun cleanUp() {
        shader.cleanUp()
    }

}