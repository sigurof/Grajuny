package no.sigurof.tutorial.lwjgl.scenario

import no.sigurof.tutorial.lwjgl.context.DefaultSceneContext
import no.sigurof.tutorial.lwjgl.renderer.Renderer
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL30
import kotlin.math.PI

const val piHalf = PI.toFloat() / 2f

class Scenario internal constructor(
    private val window: Long,
    private val renderers: List<Renderer>,
    private val context: DefaultSceneContext
) {

    fun prepare() {
        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED)
        context.camera.setCursorPosCallback(window)
    }

    fun run() {
        context.camera.move(window)
        prepareFrame()
        render()
    }

    fun cleanUp() {
        for (model in renderers) {
            model.cleanShader()
        }
    }

    private fun render() {
        for (model in renderers) {
            model.render(context)
        }
    }

    private fun prepareFrame() {
        GL30.glEnable(GL30.GL_DEPTH_TEST)
        GL30.glEnable(GL30.GL_CULL_FACE)
        GL30.glCullFace(GL30.GL_BACK)
        GL30.glClearColor(0.2f, 0.3f, 0.1f, 1.0f)
        GL30.glClear(GL30.GL_COLOR_BUFFER_BIT or GL30.GL_DEPTH_BUFFER_BIT)
    }

}