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

class BillboardScenario private constructor(
    private val camera: Camera,
    private val light: Light,
    private val window: Long,
    private val entities: Map<ModelContainer, List<Entity>>,
    private val FOV: Float = 70f,
    private val NEAR_PLANE: Float = 0.1f,
    private val FAR_PLANE: Float = 1000f
) : Scenario {

    override fun prepare() {
        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED)
        camera.setCursorPosCallback(window)
    }

    override fun run() {
        camera.move(window)
        prepareFrame()
        render()
    }

    override fun cleanUp() {
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

        fun default(window: Long): BillboardScenario {
            val texturedModel = TexturedModel.Builder()
                .withModel("sphere")
                .withTexture("default")
                .withReflectivity(10f)
                .withShineDamper(10f)
                .build()
            val billboardModel = BillboardModel()
            val light = Light.Builder()
                .position(Vector3f(0f, 10f, 5f))
                .color(Vector3f(1f, 0.2f, 0.9f))
                .ambient(0.15f)
                .build()
            val camera = Camera.Builder()
                .at(Vector3f(0f, 0f, -3f))
                .lookingAt(ORIGIN)
                .withSpeed(4f)
                .build()
            val entities = mutableMapOf<ModelContainer, List<Entity>>()
//            entities[ModelContainer(texturedModel)] =
//                mutableListOf(
//                    Entity(
//                        Vector3f(0f, 0f, 0f),
//                        Vector3f(0f, 0f, 0f),
//                        Vector3f(1f, 1f, 1f)
//                    )
//                )
            entities[ModelContainer(billboardModel)] =
                mutableListOf(
                    Entity(
                        Vector3f(0f, 0f, 0f),
                        Vector3f(0f, 0f, 0f),
                        Vector3f(1f, 1f, 1f)
                    )
                )
            return BillboardScenario(camera, light, window, entities)
        }
    }
}