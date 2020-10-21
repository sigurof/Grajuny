package no.sigurof.grajuny.game

import no.sigurof.grajuny.camera.CameraManager
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
        if (shader is LightShader) {
            LightSource.LIGHT_SOURCES.forEach {
                it.render(shader)
            }
        }
        if (shader is CameraShader) {
            (CameraManager.activeCamera ?: run {
                println("WARN: No active camera. Using default.")
                CameraManager.default()
            }).render(shader)
        }
        root.render(shader, Matrix4f().identity())
    }

    fun update() {
        CameraManager.activeCamera?.update(window)
        onUpdate()
        root.update()
        lastTime = System.currentTimeMillis()
    }

}