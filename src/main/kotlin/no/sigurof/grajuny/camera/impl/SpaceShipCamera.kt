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
    private val window: Long?,
    var parent: GameObject? = null,
    at: Vector3f,
    lookAt: Vector3f,
    upDir: Vector3f = Vector3f(0f, 1f, 0f),
    private val speed: Float = 10f,
    private val fov: Float = 70f,
    private val nearPlane: Float = 0.1f,
    private val farPlane: Float = 1000f
) : Camera {
    private val transform: Matrix4f = Matrix4f().lookAt(at, lookAt, upDir)

    init {
        CameraManager.activeCamera ?: run { CameraManager.activeCamera = this }
    }

    private var lastTime: Long = System.currentTimeMillis()
    private var lastX: Double = 400.0
    private var lastY: Double = 300.0
    private var firstMouse: Boolean = true

    private val forwards = (Vector3f(0f, 0f, 1f))
    private val backwards = (Vector3f(0f, 0f, -1f))
    private val right = Vector3f(-1f, 0f, 0f)
    private val left = Vector3f(1f, 0f, 0f)
    private val up = (Vector3f(0f, -1f, 0f))
    private val down = (Vector3f(0f, 1f, 0f))
    private val keyToMovementDirection = listOf(
        GLFW.GLFW_KEY_W to forwards,
        GLFW.GLFW_KEY_S to backwards,
        GLFW.GLFW_KEY_A to left,
        GLFW.GLFW_KEY_D to right,
        GLFW.GLFW_KEY_E to up,
        GLFW.GLFW_KEY_Q to down
    )

    override fun deactivate() {
        CameraManager.deactivateCursorCapture(window, lastX, lastY, ::mouseCallback)
    }

    override fun activate() {
        CameraManager.activateCursorCapture(window, lastX, lastY, ::mouseCallback)
    }

    override fun update(window: Long) {
        val now = System.currentTimeMillis()
        val deltaTime = (now - lastTime).toFloat() / 1000
        val scale = deltaTime * speed
        listOf(Vector3f(0f, 0f, 0f))
            .plus(keyToMovementDirection
                .filter { GLFW.glfwGetKey(window, it.first) == GLFW.GLFW_TRUE }
                .map { Vector3f(it.second) }
            )
            .reduce { acc, sum -> acc.add(sum) }
            .takeIf { it != Vector3f(0f, 0f, 0f) }
            ?.let { transform.translateLocal(it.normalize().mul(scale)) }
        lastTime = now
    }

    override fun render(shader: CameraShader) {
        val totalTransform = getTotalTransform()
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
        val quat = Quaternionf().rotateAxis(-xoffset.toFloat(), up).rotateAxis(yoffset.toFloat(), right)
        transform.rotateLocal(quat)
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

    private fun getTotalTransform(): Matrix4f {
        return getParents()
            .reversed()
            .map { it.transform }
            .plus(transform.invert(Matrix4f()))
            .reduce { acc: Matrix4f, transform: Matrix4f -> acc.mul(transform, Matrix4f()) }

    }

}