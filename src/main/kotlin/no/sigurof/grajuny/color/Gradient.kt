package no.sigurof.grajuny.color

import no.sigurof.grajuny.utils.Maths
import no.sigurof.grajuny.utils.plus
import org.joml.Vector3f

class Gradient(
    val start: Vector3f,
    val end: Vector3f
) {

    fun evaluate(value: Float): Vector3f {
        return start + end.mul(Maths.clamp(value, 0f, 1f), Vector3f())
    }

}