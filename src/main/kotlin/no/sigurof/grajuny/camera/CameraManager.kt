package no.sigurof.grajuny.camera

import no.sigurof.grajuny.camera.impl.FpsCamera
import no.sigurof.grajuny.utils.ORIGIN
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW

object CameraManager {

    var activeCamera: Camera? = null
        set(value) {
            if (value != field) {
                field?.deactivate()
                value?.activate()
                field = value
            }
        }

    fun default(): FpsCamera {
        return FpsCamera.Builder().at(ORIGIN).lookingAt(Vector3f(1f, 0f, 0f)).build()
    }

    fun activateCursorCapture(
        window: Long?,
        lastX: Double,
        lastY: Double,
        mouseCallback: (window: Long, xPos: Double, yPos: Double) -> Unit
    ) {
        window?.let {
            GLFW.glfwSetInputMode(it, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED)
            GLFW.glfwSetCursorPos(it, lastX, lastY)
            GLFW.glfwSetCursorPosCallback(it, mouseCallback)
        }
    }

    fun deactivateCursorCapture(
        window: Long?,
        lastX: Double,
        lastY: Double,
        mouseCallback: (window: Long, xPos: Double, yPos: Double) -> Unit
    ) {
        window?.let {
            GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL)
            GLFW.glfwSetCursorPosCallback(window, null)
        }
    }
}