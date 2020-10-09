package no.sigurof.grajuny.shader.interfaces

import org.joml.Vector3f

interface ColorSpecularShader {

    fun loadColor(color: Vector3f)

    fun loadSpecularValues(damper: Float, reflectivity: Float)
}