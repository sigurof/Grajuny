package no.sigurof.grajuny.shader.interfaces

import org.joml.Matrix4f

interface Shader3D {

    fun loadTransformationMatrix(transformationMatrix: Matrix4f)
}