package no.sigurof.grajuny.restructuring.shader.interfaces

import org.joml.Matrix4f
import org.joml.Vector3f

interface CameraShader {

    fun loadCameraPosition(cameraPosition: Vector3f)

    fun loadViewMatrix(viewMatrix: Matrix4f)
}