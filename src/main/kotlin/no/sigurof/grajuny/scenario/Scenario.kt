package no.sigurof.grajuny.scenario

import no.sigurof.grajuny.context.DefaultSceneContext
import no.sigurof.grajuny.renderer.Renderer
import org.joml.Vector4f
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL30

/**
 * A `Scenario` represents a single session with an open window.
 *
 * */
class Scenario constructor(
    val window: Long,
    val renderers: List<Renderer>,
    val context: DefaultSceneContext,
    val background: Vector4f = Vector4f(0.2f, 0.3f, 0.1f, 1.0f)
) {

    fun prepare() {
        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED)
        context.camera.setCursorPosCallback(window)
    }

    fun run() {
        context.camera.move(window)
        prepareFrame()
        for (model in renderers) {
            model.render(context)
        }
    }

    fun cleanUp() {
        for (model in renderers) {
            model.cleanShader()
        }
    }

    private fun prepareFrame() {
        GL30.glEnable(GL30.GL_DEPTH_TEST)
        GL30.glEnable(GL30.GL_CULL_FACE)
        GL30.glCullFace(GL30.GL_BACK)
        GL30.glClearColor(background.x, background.y, background.z, background.w)
        GL30.glClear(GL30.GL_COLOR_BUFFER_BIT or GL30.GL_DEPTH_BUFFER_BIT)
    }

}