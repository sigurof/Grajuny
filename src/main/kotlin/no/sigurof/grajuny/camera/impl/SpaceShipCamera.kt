package no.sigurof.grajuny.camera.impl

import no.sigurof.grajuny.camera.Camera
import no.sigurof.grajuny.camera.CameraManager
import no.sigurof.grajuny.shader.interfaces.CameraShader
import no.sigurof.grajuny.utils.Maths
import org.joml.Matrix4f
import org.joml.Quaternionf
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW

class SpaceShipCamera(
    private val pos: Vector3f,
    lookingAt: Vector3f,
    private val fov: Float = 70f,
    private val nearPlane: Float = 0.1f,
    private val farPlane: Float = 1000f,
    private val window: Long?,
    private val speed: Float = 10f
) : Camera {
    private val origFwAxis: Vector3f = lookingAt.sub(pos, Vector3f()).normalize()
    private val up: Vector3f = Vector3f(0f, 1f, 0f)
    private var rtAxis: Vector3f = origFwAxis.cross(up, Vector3f()).normalize() // left hand rule?, not right hand rule?
    private var origUpAxis: Vector3f = origFwAxis.cross(rtAxis, Vector3f()).negate().normalize()
    private var upAxis = origUpAxis
    private var fwAxis = origFwAxis

    private var lastTime: Long = System.currentTimeMillis()
    private var lastX: Double = 400.0
    private var lastY: Double = 300.0
    private var firstMouse: Boolean = true

    override fun deactivate() {
        CameraManager.deactivateCursorCapture(window, lastX, lastY, ::mouseCallback)
    }

    override fun activate() {
        CameraManager.activateCursorCapture(window, lastX, lastY, ::mouseCallback)
    }

    override fun update(window: Long) {
        val now = System.currentTimeMillis()
        val deltaTime = (now - lastTime).toFloat() / 1000
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_W) == 1) {
            incrementPositionInDirection(Vector3f(fwAxis), deltaTime)
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_S) == 1) {
            incrementPositionInDirection(Vector3f(fwAxis).negate(), deltaTime)
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_D) == 1) {
            incrementPositionInDirection(Vector3f(rtAxis), deltaTime)
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_A) == 1) {
            incrementPositionInDirection(Vector3f(rtAxis).negate(), deltaTime)
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_E) == 1) {
            incrementPositionInDirection(Vector3f(upAxis), deltaTime)
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_Q) == 1) {
            incrementPositionInDirection(Vector3f(upAxis).negate(), deltaTime)
        }
        lastTime = now
    }

    private fun incrementPositionInDirection(direction: Vector3f, deltaTime: Float) {
        val increment = direction.mul(deltaTime * speed, Vector3f())
        pos.add(increment)
    }

    override fun render(shader: CameraShader) {
        shader.loadCameraPosition(pos)
        shader.loadProjectionMatrix(createProjectionMatrix())
        shader.loadViewMatrix(createViewMatrix())
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
        val quat = Quaternionf().rotateAxis(-xoffset.toFloat(), upAxis).rotateAxis(yoffset.toFloat(), rtAxis)
        fwAxis.rotate(quat).normalize()
        upAxis.rotate(quat).normalize()
        rtAxis = fwAxis.cross(upAxis, Vector3f()).normalize()
        upAxis = fwAxis.cross(rtAxis, Vector3f()).negate().normalize()
    }

    private fun createProjectionMatrix(): Matrix4f {
        return Maths.createProjectionMatrix(fov, nearPlane, farPlane)
    }

    private fun createViewMatrix(): Matrix4f {
        // below: orientation * origAxis * orientationConjugated (rotate the origAxis by the orientation quaternion)

        return Maths.createViewMatrix(pos, pos.add(fwAxis, Vector3f()), upAxis)
//        return orientation.get(Matrix4f())
    }

    data class Builder(
        private var at: Vector3f? = null,
        private var lookingAt: Vector3f? = null,
        private var speed: Float = 10f,
        private var shouldCaptureMouse: Boolean = true,
        private var fov: Float = 70f,
        private var nearPlane: Float = 0.1f,
        private var farPlane: Float = 1000f,
        private var window: Long? = null
    ) {
        fun at(pos: Vector3f) = apply { this.at = pos }
        fun lookingAt(pos: Vector3f) = apply { this.lookingAt = pos }
        fun withSpeed(speed: Float) = apply { this.speed = speed }
        fun capturingMouseInput(win: Long) = apply {
            shouldCaptureMouse = true
            window = win
        }

        fun fov(value: Float) = apply { fov = value }
        fun build(): SpaceShipCamera {
            if (at?.equals(lookingAt) == true) {
                error("Camera position must be different from the position it looks towards")
            }
            val camera = SpaceShipCamera(
                pos = at ?: error("Must supply camera position"),
                lookingAt = lookingAt ?: error("Must supply point to look at"),
//                speed = speed,
                fov = fov,
                nearPlane = nearPlane,
                farPlane = farPlane,
                window = window
            )
            CameraManager.activeCamera ?: run { CameraManager.activeCamera = camera }
            return camera
        }
    }
}