package no.sigurof.grajuny.shaders.settings.impl

import no.sigurof.grajuny.shaders.settings.ShaderSettings
import org.joml.Matrix4f

interface BasicShaderSettings : ShaderSettings {

    fun loadTransformationMatrix(transformationMatrix: Matrix4f)

    fun loadProjectionMatrix(projectionMatrix: Matrix4f)

    fun loadViewMatrix(viewMatrix: Matrix4f)
}