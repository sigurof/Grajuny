package no.sigurof.grajuny.shaders.settings;

import no.sigurof.grajuny.entity.Light;
import org.joml.Matrix4f
import org.joml.Vector3f

interface DefaultShaderSettings : ShaderSettings {

    fun loadTransformationMatrix(transformationMatrix: Matrix4f)
    fun loadProjectionMatrix(projectionMatrix: Matrix4f)
    fun loadViewMatrix(viewMatrix: Matrix4f)
    fun loadLight(light: Light)
    fun loadSpecularValues(damper: Float, reflectivity: Float)
    fun loadColor(color: Vector3f)
    fun loadCameraPosition(cameraPosition: Vector3f)
}
