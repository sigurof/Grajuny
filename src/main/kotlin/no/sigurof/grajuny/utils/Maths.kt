package no.sigurof.grajuny.utils

import no.sigurof.grajuny.display.DisplayManager
import org.joml.Matrix4f
import org.joml.Vector3f
import kotlin.math.max
import kotlin.math.min

class Maths {

    companion object {

        fun clamp(value: Float, atLeast: Float, atMost: Float): Float {
            return min(max(atLeast, value), atMost)
        }

        fun createViewMatrix(pos: Vector3f, lookAt: Vector3f, up: Vector3f): Matrix4f {
            return Matrix4f().lookAt(pos, lookAt, up)
        }

        fun createProjectionMatrix(fov: Float, nearPlane: Float, farPlane: Float): Matrix4f {
            return Matrix4f()
                .perspective(
                    fov,
                    DisplayManager.WIDTH.toFloat() / DisplayManager.HEIGHT.toFloat(),
                    nearPlane,
                    farPlane
                )
        }

    }
}