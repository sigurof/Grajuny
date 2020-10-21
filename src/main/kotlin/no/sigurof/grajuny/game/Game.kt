package no.sigurof.grajuny.game

import no.sigurof.grajuny.camera.Camera
import no.sigurof.grajuny.light.LightSource
import no.sigurof.grajuny.node.GameObject
import no.sigurof.grajuny.shader.Shader
import no.sigurof.grajuny.shader.interfaces.CameraShader
import no.sigurof.grajuny.shader.interfaces.LightShader
import org.joml.Matrix4f
import org.joml.Vector4f

abstract class Game(
    val window: Long,
    val root: GameObject = GameObject.buildRoot(),
    var background: Vector4f
) {

    abstract val camera: Camera
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

    fun activate(shader: Shader) {
        if (shader is LightShader){
            LightSource.LIGHT_SOURCES.forEach {
                it.render(shader)
            }
        }
        if (shader is CameraShader){
            (Camera.activeCamera ?: run {
                println("WARN: No active camera. Using default.")
                Camera.default()
            }).render(shader)
        }
    }



}