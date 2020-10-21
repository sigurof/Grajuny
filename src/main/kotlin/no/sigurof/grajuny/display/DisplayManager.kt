package no.sigurof.grajuny.display

import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.glfwSwapBuffers
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL20
import java.lang.System.currentTimeMillis

class DisplayManager {

    companion object {
        internal var WIDTH: Int = 1280
        internal var HEIGHT: Int = 720
        var FPS = 120
        private var window: Long? = null
        private var lastUpdate: Long = currentTimeMillis()

        fun withWindowOpen(program: (window: Long) -> Unit) {
            val win = createDisplay()
            program(win)
            closeDisplay(win)
        }

        private fun createDisplay(): Long {
            GLFWErrorCallback.createPrint(System.err).set();
            if (!GLFW.glfwInit())
                throw IllegalStateException("Unable to initialize GLFW");
            GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL20.GL_TRUE)
            GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 4)
            GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2)
            GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL20.GL_TRUE)
            GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE)
            window = GLFW.glfwCreateWindow(
                WIDTH,
                HEIGHT, "Hello Window", 0, 0
            )
            if (window == null || window == 0L) {
                throw RuntimeException("Failed to create window!")
            } else {
                GLFW.glfwMakeContextCurrent(window!!)
                GLFW.glfwShowWindow(window!!)
                GL.createCapabilities()
            }
            GLFW.glfwSetWindowSizeCallback(window!!, Companion::windowResizeCallback)
            return window!!
        }

        private fun windowResizeCallback(w: Long, width: Int, height: Int) {
            WIDTH = width
            HEIGHT = height
            GL20.glViewport(0, 0, WIDTH, HEIGHT)
        }

        fun eachFrameDo(func: (() -> Unit)) {
            while (isOpen()){
                window
                    ?.let {
                        val now = currentTimeMillis()
                        if (now - lastUpdate > (1000 / FPS).toLong()) {
                            func.invoke()
                            lastUpdate = now
                        }
                        GLFW.glfwPollEvents()
                        glfwSwapBuffers(it)
                    }
            }
        }

        private fun closeDisplay(window: Long) {
            GLFW.glfwDestroyWindow(window)
        }

        private fun isOpen(): Boolean {
            return window?.let { !GLFW.glfwWindowShouldClose(it) } ?: false
        }
    }
}