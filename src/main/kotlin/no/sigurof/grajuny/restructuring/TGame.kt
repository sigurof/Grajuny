package no.sigurof.grajuny.restructuring

import no.sigurof.grajuny.restructuring.node.GameObject
import no.sigurof.grajuny.restructuring.shader.Shader
import no.sigurof.grajuny.restructuring.shader.interfaces.CameraShader
import no.sigurof.grajuny.restructuring.shader.interfaces.LightShader
import no.sigurof.grajuny.restructuring.shader.interfaces.ProjectionMatrixShader
import no.sigurof.grajuny.utils.Maths
import org.joml.Matrix4f
import org.joml.Vector4f

abstract class TGame(
    val window: Long,
    val root: GameObject = GameObject(),
    var background: Vector4f,
    private val fov: Float = 70f,
    private val nearPlane: Float = 0.1f,
    private val farPlane: Float = 1000f
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

    fun render(shader: Shader) {
        root.update()
        root.render(shader, Matrix4f().identity())
        lastTime = System.currentTimeMillis()
    }

    private fun createProjectionMatrix(): Matrix4f {
        return Maths.createProjectionMatrix(fov, nearPlane, farPlane)
    }

    private fun createViewMatrix(): Matrix4f {
        return Maths.createViewMatrix(camera)
    }

    fun doNecessaryStuff(shader: Shader) {
        if (shader is ProjectionMatrixShader) {
            shader.loadProjectionMatrix(createProjectionMatrix())
        }
        if (shader is LightShader){
            LightSource.LIGHT_SOURCES.forEach {
                it.render(shader)
            }
        }
        if (shader is CameraShader){
            (TCamera.activeCamera ?: run {
                println("WARN: No active camera. Using default.")
                TCamera.default()
            }).render(shader)
            shader.loadViewMatrix(createViewMatrix())
        }
    }



}