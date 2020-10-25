package no.sigurof.grajuny.camera.impl

import no.sigurof.grajuny.camera.Camera
import no.sigurof.grajuny.camera.CameraManager
import no.sigurof.grajuny.node.GameObject
import no.sigurof.grajuny.shader.interfaces.CameraShader
import no.sigurof.grajuny.utils.Maths
import org.joml.Matrix4f
import org.joml.Vector3f

class CompCamera(
    private val fov: Float = 70f,
    private val nearPlane: Float = 0.1f,
    private val farPlane: Float = 1000f,
    private val window: Long?,
    private val transform: Matrix4f,
    private val origFwVector: Vector3f = Vector3f(0f, 0f, 1f)
) : Camera {

    init {
        CameraManager.activeCamera ?: run { CameraManager.activeCamera = this }
    }

    var parent: GameObject? = null
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

    }

    override fun render(shader: CameraShader) {
        val compositeTransform = getCompositeTransform()
        val pos = compositeTransform.getColumn(3, Vector3f())
        shader.loadCameraPosition(pos)
        shader.loadProjectionMatrix(createProjectionMatrix())
        val fw = compositeTransform.transformDirection(origFwVector, Vector3f())
        shader.loadViewMatrix(
            createViewMatrix(
                pos,
                fw,
                Vector3f(0f, 1f, 0f)
            )
        )
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
    }

    private fun createProjectionMatrix(): Matrix4f {
        return Maths.createProjectionMatrix(fov, nearPlane, farPlane)
    }

    private fun createViewMatrix(pos: Vector3f, fw: Vector3f, up: Vector3f): Matrix4f {
        val lookAt = pos.add(fw, Vector3f())
        return Maths.createViewMatrix(
            pos,
            lookAt,
            up
        )
//        return orientation.get(Matrix4f())
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
        val plus = getParents().reversed().map { it.transform }.plus(this.transform)
//        val plus = listOf(this.transform).plus(getParents().map { it.transform }).reversed()
        return plus
            .reduce { acc: Matrix4f, transform: Matrix4f -> acc.mul(transform, Matrix4f()) }

    }

}