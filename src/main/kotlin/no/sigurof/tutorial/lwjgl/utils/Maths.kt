package no.sigurof.tutorial.lwjgl.utils

import no.sigurof.tutorial.lwjgl.entity.Camera
import no.sigurof.tutorial.lwjgl.entity.Entity
import org.joml.Matrix4f
import org.joml.Vector3f

class Maths {

    companion object {
        public val x = Vector3f(1f, 0f, 0f)
        public val y = Vector3f(0f, 1f, 0f)
        public val z = Vector3f(0f, 0f, 1f)

        public fun createTransformationMatrix(entity: Entity): Matrix4f {
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
            val viewMatrix = Matrix4f()
            viewMatrix.identity()
            viewMatrix.rotate(camera.pitch, x)
            viewMatrix.rotate(camera.yaw, y)
            val negCameraPos = camera.position.negate(Vector3f())
            viewMatrix.translate(negCameraPos)
            return viewMatrix
        }

    }
}