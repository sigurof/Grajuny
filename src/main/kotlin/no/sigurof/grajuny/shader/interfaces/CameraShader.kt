package no.sigurof.grajuny.shader.interfaces

import org.joml.Matrix4f
import org.joml.Vector3f

interface CameraShader {

    fun loadProjectionMatrix(projectionMatrix: Matrix4f)

    fun loadCameraPosition(cameraPosition: Vector3f)

    fun loadViewMatrix(viewMatrix: Matrix4f)
}