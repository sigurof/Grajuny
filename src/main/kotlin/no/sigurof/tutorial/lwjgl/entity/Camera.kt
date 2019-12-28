package no.sigurof.tutorial.lwjgl.entity

import org.joml.Vector3f
import org.lwjgl.glfw.GLFW.*

class Camera private constructor(
    internal val pos: Vector3f,
    lookingAt: Vector3f,
    private val window: Long,
    private val speed: Float
) {
    private val up: Vector3f = Vector3f(0f, 1f, 0f)
    internal val fwAxis: Vector3f = lookingAt.sub(pos, Vector3f()).normalize()
    private val rtAxis: Vector3f = fwAxis.cross(up, Vector3f()).normalize() // left hand rule?, not right hand rule?
    internal val upAxis: Vector3f = fwAxis.cross(rtAxis, Vector3f()).negate().normalize()
    private var lastTime: Long = System.currentTimeMillis()

    data class Builder(
        private var at: Vector3f? = null,
        private var lookingAt: Vector3f? = null,
        private var window: Long? = null,
        private var speed: Float = 10f
    ) {
        fun at(pos: Vector3f) = apply { this.at = pos }
        fun lookingAt(pos: Vector3f) = apply { this.lookingAt = pos }
        fun inWindow(window: Long) = apply { this.window = window }
        fun withSpeed(speed: Float) = apply { this.speed = speed }
        fun build(): Camera {
            if (at?.equals(lookingAt) == true) {
                error("Camera position must be different from the position it looks towards")
            }
            return Camera(
                at ?: error("Must supply camera position"),
                lookingAt ?: error("Must supply point to look at"),
                window ?: error("Must supply glfw window"),
                speed
            )
        }
    }

    fun move() {
        val now = System.currentTimeMillis()
        val deltaTime = (now - lastTime).toFloat() / 1000
        if (glfwGetKey(window, GLFW_KEY_W) == 1) {
            incrementPositionInDirection(Vector3f(fwAxis), deltaTime)
        }
        if (glfwGetKey(window, GLFW_KEY_S) == 1) {
            incrementPositionInDirection(Vector3f(fwAxis).negate(), deltaTime)
        }
        if (glfwGetKey(window, GLFW_KEY_D) == 1) {
            incrementPositionInDirection(Vector3f(rtAxis), deltaTime)
        }
        if (glfwGetKey(window, GLFW_KEY_A) == 1) {
            incrementPositionInDirection(Vector3f(rtAxis).negate(), deltaTime)
        }
        if (glfwGetKey(window, GLFW_KEY_E) == 1) {
            incrementPositionInDirection(Vector3f(upAxis), deltaTime)
        }
        if (glfwGetKey(window, GLFW_KEY_Q) == 1) {
            incrementPositionInDirection(Vector3f(upAxis).negate(), deltaTime)
        }
        lastTime = now
    }

    private fun incrementPositionInDirection(direction: Vector3f, deltaTime: Float) {
        val increment = direction.mul(deltaTime * speed, Vector3f())
        pos.add(increment)
    }


}