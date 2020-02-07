package no.sigurof.grajuny.context

import no.sigurof.grajuny.entity.Camera
import no.sigurof.grajuny.shaders.settings.impl.BasicShaderSettings
import no.sigurof.grajuny.utils.Maths

class BasicSceneContext(
    private val fov: Float = 70f,
    private val nearPlane: Float = 0.1f,
    private val farPlane: Float = 1000f,
    private val camera: Camera
) : GlobalContext<BasicShaderSettings> {
    override fun loadUniforms(shader: BasicShaderSettings) {
        shader.loadProjectionMatrix(Maths.createProjectionMatrix(fov, nearPlane, farPlane))
        shader.loadViewMatrix(Maths.createViewMatrix(camera))
    }
}