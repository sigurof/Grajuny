package no.sigurof.tutorial.lwjgl.scenario

import no.sigurof.tutorial.lwjgl.entity.Camera
import no.sigurof.tutorial.lwjgl.entity.Entity
import no.sigurof.tutorial.lwjgl.entity.Light
import no.sigurof.tutorial.lwjgl.model.BillboardModel
import no.sigurof.tutorial.lwjgl.model.ModelContainer
import no.sigurof.tutorial.lwjgl.model.TexturedModel
import no.sigurof.tutorial.lwjgl.utils.ORIGIN
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL30

class Scenario private constructor(
    private val camera: Camera,
    private val light: Light,
    private val window: Long,
    private val entities: Map<ModelContainer, List<Entity>>,
    private val FOV: Float = 70f,
    private val NEAR_PLANE: Float = 0.1f,
    private val FAR_PLANE: Float = 1000f
) {

     fun prepare() {
        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED)
        camera.setCursorPosCallback(window)
    }

     fun run() {
        camera.move(window)
        prepareFrame()
        render()
    }

     fun cleanUp() {
        for (container in entities.keys) {
            container.cleanupShader()
        }
    }

    private fun render() {
        // Flytt følgende til scene
        for ((model, ents) in entities) {
            model.render(ents, light, camera, FOV, NEAR_PLANE, FAR_PLANE)
        }
    }

    private fun prepareFrame() {
        GL30.glEnable(GL30.GL_DEPTH_TEST)
        GL30.glEnable(GL30.GL_CULL_FACE)
        GL30.glCullFace(GL30.GL_BACK) // TODO This disables billboard
        GL30.glClearColor(0.2f, 0.3f, 0.1f, 1.0f)
        GL30.glClear(GL30.GL_COLOR_BUFFER_BIT or GL30.GL_DEPTH_BUFFER_BIT)
    }

    companion object {

        fun default(window: Long): Scenario {
            val blue = Vector3f(0.1f, 0.4f, 0.9f)
            val reflectivity = 1f
            val damper = 100f
            val texturedModel = TexturedModel.Builder()
                .withColor(blue)
                .withModel("sphere")
                .withTexture("default")
                .withReflectivity(reflectivity)
                .withShineDamper(damper)
                .build()
            val billboardModel = BillboardModel(
                reflectivity = reflectivity,
                damper = damper,
                color = blue
            )
            val light = Light.Builder()
                .position(Vector3f(0f, 11f, 0f))
                .ambient(0.15f)
                .build()
            val camera = Camera.Builder()
                .at(Vector3f(0f, 0f, -3f))
                .lookingAt(ORIGIN)
                .withSpeed(4f)
                .build()
            val entities = mutableMapOf<ModelContainer, List<Entity>>()
            entities[ModelContainer(texturedModel)] =
                mutableListOf(
                    Entity(
                        Vector3f(-1f, 0f, 0f),
                        Vector3f(0f, 0f, 0f),
                        Vector3f(1f, 1f, 1f)
                    )
                )
            entities[ModelContainer(billboardModel)] =
                mutableListOf(
                    Entity(
                        Vector3f(1f, 0f, 0f),
                        Vector3f(0f, 0f, 0f),
                        Vector3f(1f, 1f, 1f)
                    )
                )
            return Scenario(camera, light, window, entities)
        }
    }
}