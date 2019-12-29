package no.sigurof.tutorial.lwjgl.scenario

import no.sigurof.tutorial.lwjgl.engine.DisplayManager
import no.sigurof.tutorial.lwjgl.entity.Camera
import no.sigurof.tutorial.lwjgl.entity.SphereBillboard
import no.sigurof.tutorial.lwjgl.shaders.BillboardShader
import no.sigurof.tutorial.lwjgl.utils.Maths
import no.sigurof.tutorial.lwjgl.utils.ORIGIN
import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30.glBindVertexArray
import org.lwjgl.opengl.GL30.glGenVertexArrays

class BillboardScenario private constructor(
    private val camera: Camera,
    private val shader: BillboardShader,
    private val billboard: SphereBillboard,
    private val window: Long,
    private val FOV: Float = 70f,
    private val NEAR_PLANE: Float = 0.1f,
    private val FAR_PLANE: Float = 1000f
) : Scenario {

    companion object {
        fun default(window: Long): BillboardScenario {
            val vao = glGenVertexArrays()
            glBindVertexArray(vao)
            val shader = BillboardShader()
            val billboard = SphereBillboard()
            val camera = Camera.Builder()
                .at(Vector3f(0f, 0f, -3f))
                .lookingAt(ORIGIN)
                .withSpeed(4f)
                .build()
            return BillboardScenario(camera, shader, billboard, window)
        }
    }

    override fun prepare() {
        uploadProjectionMatrix(shader)
        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED)
        camera.setCursorPosCallback(window)
    }

    override fun run() {
        camera.move(window)
        prepareFrame()
        shader.start()
        shader.loadViewMatrix(Maths.createViewMatrix(camera))
        billboard.render(shader)
        shader.stop()
    }

    override fun cleanUp() {
        shader.cleanUp()
        glBindVertexArray(0)
    }

    private fun prepareFrame() {
        GL11.glClearColor(0.2f, 0.3f, 0.1f, 1.0f)
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT)
    }


    private fun uploadProjectionMatrix(shader: BillboardShader) {
        val projectionMatrix = Matrix4f()
            .perspective(
                FOV,
                DisplayManager.WIDTH.toFloat() / DisplayManager.HEIGHT.toFloat(),
                NEAR_PLANE,
                FAR_PLANE
            )
        shader.start()
        shader.loadProjectionMatrix(projectionMatrix)
        shader.stop()
    }

}