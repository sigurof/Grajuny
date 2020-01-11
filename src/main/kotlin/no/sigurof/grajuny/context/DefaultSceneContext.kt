package no.sigurof.grajuny.context

import no.sigurof.grajuny.entity.Camera
import no.sigurof.grajuny.entity.Light
import no.sigurof.grajuny.shaders.settings.DefaultShaderSettings
import no.sigurof.grajuny.shaders.settings.ShaderSettings
import no.sigurof.grajuny.utils.Maths

class DefaultSceneContext constructor(
    private val fov: Float = 70f,
    private val nearPlane: Float = 0.1f,
    private val farPlane: Float = 1000f,
    internal val camera: Camera,
    private val light: Light
) : GlobalContext {

    override fun loadUniforms(shader: ShaderSettings) {
        val shader = shader as DefaultShaderSettings
        shader.loadLight(light)
        shader.loadProjectionMatrix(Maths.createProjectionMatrix(fov, nearPlane, farPlane))
        shader.loadViewMatrix(Maths.createViewMatrix(camera))
        shader.loadCameraPosition(camera.pos)
    }
}