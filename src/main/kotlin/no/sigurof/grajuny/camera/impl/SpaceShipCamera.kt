package no.sigurof.grajuny.camera.impl

import no.sigurof.grajuny.camera.Camera
import no.sigurof.grajuny.camera.CameraManager
import no.sigurof.grajuny.node.GameObject
import no.sigurof.grajuny.shader.interfaces.CameraShader
import no.sigurof.grajuny.utils.Maths
import org.joml.Matrix4f
import org.joml.Quaternionf
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW

class SpaceShipCamera(
    private val fov: Float = 70f,
    private val nearPlane: Float = 0.1f,
    private val farPlane: Float = 1000f,
    private val window: Long?,
    private val transform: Matrix4f
) : Camera {
    init {
        CameraManager.activeCamera ?: run { CameraManager.activeCamera = this }
    }

    var parent: GameObject? = null
    private var lastTime: Long = System.currentTimeMillis()
    private var lastX: Double = 400.0
    private var lastY: Double = 300.0
    private var firstMouse: Boolean = true

    private val fwAxis = (Vector3f(0f, 0f, -1f))
    private val rtAxis = (Vector3f(1f, 0f, 0f))
    private val upAxis = (Vector3f(0f, 1f, 0f))

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
        val increment = direction.mul(deltaTime * 10f, Vector3f())
        transform.translate(increment)
    }

    override fun render(shader: CameraShader) {
        val totalTransform = getCompositeTransform().mul(transform, Matrix4f())
        val pos = totalTransform.getColumn(3, Vector3f())
        shader.loadCameraPosition(pos)
        shader.loadProjectionMatrix(createProjectionMatrix())
        shader.loadViewMatrix(totalTransform.invert(Matrix4f()))
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
        transform.rotate(quat)
    }

    private fun createProjectionMatrix(): Matrix4f {
        return Maths.createProjectionMatrix(fov, nearPlane, farPlane)
    }

    private fun getParents(): List<GameObject> {
        val parents = mutableListOf<GameObject>()
        var currentParent: GameObject? = this.parent
        while (currentParent != null) {
            parents.add(currentParent)
            currentParent = currentParent.parent
        }
        return parents
    }

    private fun getCompositeTransform(): Matrix4f {
        return getParents()
            .reversed()
            .map { it.transform }
            .reduce { acc: Matrix4f, transform: Matrix4f -> acc.mul(transform, Matrix4f()) }

    }

}