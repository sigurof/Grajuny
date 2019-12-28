package no.sigurof.tutorial.lwjgl.utils

import no.sigurof.tutorial.lwjgl.entity.Camera
import no.sigurof.tutorial.lwjgl.entity.Entity
import org.joml.Matrix4f
import org.joml.Vector3f

class Maths {

    companion object {
        private val x = Vector3f(1f, 0f, 0f)
        private val y = Vector3f(0f, 1f, 0f)
        private val z = Vector3f(0f, 0f, 1f)

        fun createTransformationMatrix(entity: Entity): Matrix4f {
            val matrix4f = Matrix4f()
            matrix4f.identity()
            matrix4f.translate(entity.position)
            matrix4f.rotate(entity.eulerAngles.x, x)
            matrix4f.rotate(entity.eulerAngles.y, y)
            matrix4f.rotate(entity.eulerAngles.z, z)
            matrix4f.scale(entity.scale)
            return matrix4f
        }

        fun createTransformationMatrix(translation: Vector3f, euler: Vector3f, scale: Vector3f): Matrix4f {
            val matrix4f = Matrix4f()
            matrix4f.identity()
            matrix4f.translate(translation)
            matrix4f.rotate(euler.x, Vector3f(1f, 0f, 0f))
            matrix4f.rotate(euler.y, Vector3f(0f, 1f, 0f))
            matrix4f.rotate(euler.z, Vector3f(0f, 0f, 1f))
            matrix4f.scale(scale)
            return matrix4f
        }

        fun createViewMatrix(camera: Camera): Matrix4f {
            return Matrix4f().lookAt(
                camera.pos,
                camera.pos.add(camera.fwAxis, Vector3f()),
                camera.upAxis
            )
        }
    }
}