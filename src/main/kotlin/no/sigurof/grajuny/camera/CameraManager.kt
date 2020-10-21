package no.sigurof.grajuny.camera

import no.sigurof.grajuny.utils.ORIGIN
import org.joml.Vector3f

object CameraManager {

    var activeCamera: Camera? = null
        set(value) {
            if (value != field) {
                field?.deactivate()
                value?.activate()
                field = value
            }
        }

    fun default(): DefaultCamera {
        return DefaultCamera.Builder().at(ORIGIN).lookingAt(Vector3f(1f, 0f, 0f)).build()
    }
}