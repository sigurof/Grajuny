package no.sigurof.grajuny.utils

import org.joml.Vector3f

operator fun Vector3f.plus(other: Vector3f): Vector3f {
    return this.add(other, Vector3f())
}

