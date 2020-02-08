package no.sigurof.grajuny.experimental2

import org.joml.Vector3f

class CameraData(
    val eye: Vector3f,
    val target: Vector3f,
    private val up: Vector3f,
    val fovy: Float,
    val znear: Float,
    val zfar: Float
)
