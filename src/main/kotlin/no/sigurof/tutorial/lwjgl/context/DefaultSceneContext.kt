package no.sigurof.tutorial.lwjgl.context

import no.sigurof.tutorial.lwjgl.entity.Camera
import no.sigurof.tutorial.lwjgl.entity.Light
import no.sigurof.tutorial.lwjgl.shaders.settings.DefaultShaderSettings
import no.sigurof.tutorial.lwjgl.utils.Maths

class DefaultSceneContext constructor(
    private val fov: Float = 70f,
    private val nearPlane: Float = 0.1f,
    private val farPlane: Float = 1000f,
    internal val camera: Camera,
    private val light: Light
) : GlobalContext<DefaultShaderSettings> {

    override fun loadUniforms(shader: DefaultShaderSettings) {
        shader.loadLight(light)
        shader.loadProjectionMatrix(Maths.createProjectionMatrix(fov, nearPlane, farPlane))
        shader.loadViewMatrix(Maths.createViewMatrix(camera))
    }
}