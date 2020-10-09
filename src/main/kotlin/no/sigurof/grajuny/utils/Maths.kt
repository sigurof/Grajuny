package no.sigurof.grajuny.utils

import no.sigurof.grajuny.camera.TCamera
import no.sigurof.grajuny.display.TDisplayManager
import org.joml.Matrix4f
import org.joml.Vector3f

class Maths {

    companion object {
        private val x = Vector3f(1f, 0f, 0f)
        private val y = Vector3f(0f, 1f, 0f)
        private val z = Vector3f(0f, 0f, 1f)

        fun createTransformationMatrix(position: Vector3f, eulerAngles: Vector3f, scale: Vector3f): Matrix4f {
            val matrix4f = Matrix4f()
            matrix4f.identity()
            matrix4f.translate(position)
            matrix4f.rotate(eulerAngles.x, x)
            matrix4f.rotate(eulerAngles.y, y)
            matrix4f.rotate(eulerAngles.z, z)
            matrix4f.scale(scale)
            return matrix4f
        }

        fun createViewMatrix(camera: TCamera): Matrix4f {
            return Matrix4f().lookAt(
                camera.pos,
                camera.pos.add(camera.fwAxis, Vector3f()),
                camera.upAxis
            )
        }

        fun createProjectionMatrix(fov: Float, nearPlane: Float, farPlane: Float): Matrix4f {
            return Matrix4f()
                .perspective(
                    fov,
                    TDisplayManager.WIDTH.toFloat() / TDisplayManager.HEIGHT.toFloat(),
                    nearPlane,
                    farPlane
                )
        }

    }
}