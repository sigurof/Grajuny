package no.sigurof.grajuny.light.phong

import no.sigurof.grajuny.light.Light
import org.joml.Vector3f

data class PointLight(
    val position: Vector3f,
    val constant: Float,
    val linear: Float,
    val quadratic: Float,
    val ambient: Vector3f,
    val diffuse: Vector3f,
    val specular: Vector3f
) : Light {

}