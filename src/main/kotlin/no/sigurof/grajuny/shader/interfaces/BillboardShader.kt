package no.sigurof.grajuny.shader.interfaces

import org.joml.Vector3f

interface BillboardShader {

    fun loadSphereCenter(sphereCenter: Vector3f)
    fun loadSphereRadius(radius: Float)
}