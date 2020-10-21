package no.sigurof.grajuny.camera

import no.sigurof.grajuny.shader.interfaces.CameraShader

interface Camera {
    fun deactivate()
    fun activate()
    fun update(window: Long)
    fun render(shader: CameraShader)
}