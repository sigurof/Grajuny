package no.sigurof.tutorial.lwjgl.engine

import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.glfwSwapBuffers
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL20
import java.lang.System.currentTimeMillis

class DisplayManager {

    companion object {
        val WIDTH: Int = 1280
        val HEIGHT: Int = 720
        var FPS = 120
        var window: Long? = null
        var lastUpdate: Long = currentTimeMillis()

        fun withWindowOpen(program: (window: Long) -> Unit) {
            val win = createDisplay()
            program(win)
            closeDisplay()
            Loader.cleanUp()
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
            return window!!
        }

        fun eachFrameDo(func: (() -> Unit)) {
            window
                ?.let {
                    var now = currentTimeMillis()
                    if (now - lastUpdate > (1000 / FPS).toLong()) {
                        func.invoke()
                        lastUpdate = now
                    }
                    GLFW.glfwPollEvents()
                    glfwSwapBuffers(it)
                }
        }

        fun closeDisplay() {
            window?.let {
                GLFW.glfwDestroyWindow(it)
            }
        }


        fun isOpen(): Boolean {
            return window?.let { !GLFW.glfwWindowShouldClose(it) } ?: false
        }
    }
}