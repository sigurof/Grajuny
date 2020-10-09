package no.sigurof.grajuny.restructuring.shader.interfaces

import org.joml.Matrix4f

interface ProjectionMatrixShader {

    fun loadProjectionMatrix(projectionMatrix: Matrix4f)
}