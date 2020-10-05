package no.sigurof.grajuny.restructuring

import no.sigurof.grajuny.restructuring.node.GameObject
import no.sigurof.grajuny.utils.Maths
import org.joml.Matrix4f
import org.joml.Vector4f

abstract class TGame(
    val window: Long,
    val root: GameObject = GameObject(),
    var background: Vector4f,
    val fov: Float = 70f,
    val nearPlane: Float = 0.1f,
    val farPlane: Float = 1000f
) {

    abstract val camera: TCamera
    private var startTime: Long = System.currentTimeMillis()
    private var lastTime: Long = System.currentTimeMillis()
    internal val deltaTime: Long
        get() {
            return System.currentTimeMillis() - lastTime
        }
    internal val elapsedMs: Long
        get() {
            return System.currentTimeMillis() - startTime
        }

    abstract fun onUpdate()

    fun input() {
        root.input()
    }

    fun update() {
        root.update()
    }

    fun render(shader: Shader) {
        root.update()
        root.render(shader, Matrix4f().identity())
        lastTime = System.currentTimeMillis()
    }

    fun createProjectionMatrix(): Matrix4f {
        return Maths.createProjectionMatrix(fov, nearPlane, farPlane)
    }

    fun createViewMatrix(): Matrix4f {
        return Maths.createViewMatrix(camera)
    }

    fun doNecessaryStuff(shader: Shader) {
        if (shader is BasicShader) {
            shader.loadProjectionMatrix(createProjectionMatrix())
            LightSource.LIGHT_SOURCES.forEach {
                it.render(shader)
            }
            (TCamera.activeCamera ?: run {
                println("WARN: No active camera. Using default.")
                TCamera.default()
            }).render(shader)
            shader.loadViewMatrix(createViewMatrix())
        }
    }

}