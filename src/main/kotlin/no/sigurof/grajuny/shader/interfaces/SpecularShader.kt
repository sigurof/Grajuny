package no.sigurof.grajuny.shader.interfaces

interface SpecularShader {
    fun loadSpecularValues(damper: Float, reflectivity: Float)
}