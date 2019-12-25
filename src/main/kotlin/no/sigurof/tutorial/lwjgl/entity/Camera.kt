package no.sigurof.tutorial.lwjgl.entity

import org.joml.Vector3f
import org.lwjgl.glfw.GLFW.*

class Camera(
    internal val position: Vector3f = Vector3f(0f, 0f, 10f),
    internal val pitch: Float = 0f,
    internal val yaw: Float = 0f,
    internal val roll: Float = 0f,
    private val window: Long
) {

    fun move() {
        if (glfwGetKey(window, GLFW_KEY_W) == 1) {
            position.z -= 0.02f
        }
        if (glfwGetKey(window, GLFW_KEY_S) == 1) {
            position.z += 0.02f
        }
        if (glfwGetKey(window, GLFW_KEY_D) == 1) {
            position.x += 0.02f
        }
        if (glfwGetKey(window, GLFW_KEY_A) == 1) {
            position.x -= 0.02f
        }
    }
}