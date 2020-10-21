package no.sigurof.grajuny.camera

import no.sigurof.grajuny.shader.interfaces.CameraShader
import no.sigurof.grajuny.utils.Maths
import no.sigurof.grajuny.utils.ORIGIN
import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.GLFW_KEY_A
import org.lwjgl.glfw.GLFW.GLFW_KEY_D
import org.lwjgl.glfw.GLFW.GLFW_KEY_E
import org.lwjgl.glfw.GLFW.GLFW_KEY_Q
import org.lwjgl.glfw.GLFW.GLFW_KEY_S
import org.lwjgl.glfw.GLFW.GLFW_KEY_W
import org.lwjgl.glfw.GLFW.glfwGetKey
import org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback

class Camera private constructor(
    internal val pos: Vector3f,
    lookingAt: Vector3f,
    private val speed: Float,
    val fov: Float = 70f,
    val nearPlane: Float = 0.1f,
    val farPlane: Float = 1000f
) {
    private val up: Vector3f = Vector3f(0f, 1f, 0f)
    internal val fwAxis: Vector3f = lookingAt.sub(pos, Vector3f()).normalize()
    private var rtAxis: Vector3f = fwAxis.cross(up, Vector3f()).normalize() // left hand rule?, not right hand rule?
    internal var upAxis: Vector3f = fwAxis.cross(rtAxis, Vector3f()).negate().normalize()
    private var lastTime: Long = System.currentTimeMillis()
    private var lastX: Double = 400.0
    private var lastY: Double = 300.0
    private var firstMouse: Boolean = true
    private var nextDirection: Vector3f? = null

    data class Builder(
        private var at: Vector3f? = null,
        private var lookingAt: Vector3f? = null,
        private var speed: Float = 10f,
        private var shouldCaptureMouse: Boolean = true,
        private var window: Long? = null
    ) {
        fun at(pos: Vector3f) = apply { this.at = pos }
        fun lookingAt(pos: Vector3f) = apply { this.lookingAt = pos }
        fun withSpeed(speed: Float) = apply { this.speed = speed }
        fun capturingMouseInput(win: Long) = apply {
            shouldCaptureMouse = true
            window = win
        }

        fun build(): Camera {
            if (at?.equals(lookingAt) == true) {
                error("Camera position must be different from the position it looks towards")
            }
            val camera = Camera(
                at ?: error("Must supply camera position"),
                lookingAt ?: error("Must supply point to look at"),
                speed
            )
            activeCamera = camera
            window?.takeIf { shouldCaptureMouse }?.let { camera.activateCursor(it) }
            return camera
        }
    }

    fun move(window: Long) {
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

    private fun mouseCallback(window: Long, xpos: Double, ypos: Double): Unit {
        if (firstMouse) {
            lastX = xpos
            lastY = ypos
            firstMouse = false
        }
        var xoffset = xpos - lastX
        var yoffset = lastY - ypos
        lastX = xpos
        lastY = ypos
        val sensitivity = 0.005f
        xoffset *= sensitivity
        yoffset *= sensitivity
        recalculateAxes(xoffset, yoffset)
    }

    private fun recalculateAxes(xoffset: Double, yoffset: Double) {
        val newFwAxis = Vector3f(fwAxis)
        newFwAxis.add(Vector3f(upAxis).mul(yoffset.toFloat()))
        newFwAxis.add(Vector3f(rtAxis).mul(xoffset.toFloat()))
        newFwAxis.normalize()
        fwAxis.set(newFwAxis)
        rtAxis = fwAxis.cross(up, Vector3f()).normalize()
        upAxis = fwAxis.cross(rtAxis, Vector3f()).negate().normalize()
    }

    private fun incrementPositionInDirection(direction: Vector3f, deltaTime: Float) {
        val increment = direction.mul(deltaTime * speed, Vector3f())
        pos.add(increment)
    }

    fun setCursorPosCallback(window: Long) {
        glfwSetCursorPosCallback(window, ::mouseCallback)
    }

    fun render(shader: CameraShader) {
        shader.loadCameraPosition(pos)
        shader.loadProjectionMatrix(createProjectionMatrix())
        shader.loadViewMatrix(createViewMatrix())
    }

    fun activateCursor(window: Long) {
        setCursorPosCallback(window)
        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED)
    }

    private fun createProjectionMatrix(): Matrix4f {
        return Maths.createProjectionMatrix(fov, nearPlane, farPlane)
    }

    private fun createViewMatrix(): Matrix4f {
        return Maths.createViewMatrix(pos, pos.add(fwAxis, Vector3f()), upAxis)
    }

    companion object {
        var activeCamera: Camera? = null

        fun default(): Camera {
            return Builder().at(ORIGIN).lookingAt(Vector3f(1f, 0f, 0f)).build()
        }
    }

}