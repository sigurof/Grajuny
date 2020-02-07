package no.sigurof.grajuny.shaders.settings;

import no.sigurof.grajuny.entity.Light;
import no.sigurof.grajuny.shaders.settings.impl.BasicShaderSettings
import org.joml.Matrix4f
import org.joml.Vector3f

interface DefaultShaderSettings : BasicShaderSettings {

    override fun loadTransformationMatrix(transformationMatrix: Matrix4f)
    override fun loadProjectionMatrix(projectionMatrix: Matrix4f)
    override fun loadViewMatrix(viewMatrix: Matrix4f)
    fun loadLight(light: Light)
    fun loadSpecularValues(damper: Float, reflectivity: Float)
    fun loadColor(color: Vector3f)
    fun loadCameraPosition(cameraPosition: Vector3f)
}
