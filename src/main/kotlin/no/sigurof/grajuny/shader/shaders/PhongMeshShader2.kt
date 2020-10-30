package no.sigurof.grajuny.shader.shaders

import no.sigurof.grajuny.light.LightSource
import no.sigurof.grajuny.shader.Shader
import no.sigurof.grajuny.shader.ShaderManager
import no.sigurof.grajuny.shader.interfaces.CameraShader
import no.sigurof.grajuny.shader.interfaces.ColorShader
import no.sigurof.grajuny.shader.interfaces.LightShader
import no.sigurof.grajuny.shader.interfaces.Shader3D
import no.sigurof.grajuny.shader.interfaces.SpecularShader
import no.sigurof.grajuny.shader.interfaces.TextureShader
import org.joml.Matrix4f
import org.joml.Vector3f

const val trMatrix = "trMatrix"

const val prjMatrix = "prjMatrix"

const val viewMatrix = "viewMatrix"

const val lightPos = "lightPos"

const val cameraPos = "cameraPos"

const val lightCol = "lightCol"

const val shineDamper = "shineDamper"

const val reflectivity = "reflectivity"

const val ambientStrength = "ambientStrength"

const val color = "color"

const val useTexture = "useTexture"

const val useSpec = "useSpecular"

const val useDiffuse = "useDiffuse"

object PhongMeshShader2 : Shader(
    vtxSource = "/shader/mesh/newphong/vertex.shader",
    frgSource = "/shader/mesh/newphong/fragment.shader",
    attributes = listOf(
        0 to "position",
        1 to "textureCoords",
        2 to "normal"
    ),
    uniforms = listOf(
        trMatrix,
        prjMatrix,
        viewMatrix,
        lightPos,
        cameraPos,
        lightCol,
        shineDamper,
        reflectivity,
        ambientStrength,
        color,
        useTexture,
        useSpec,
        useDiffuse
    )
),
    TextureShader,
    ColorShader,
    SpecularShader,
    Shader3D,
    CameraShader,
    LightShader {

    override fun loadTransformationMatrix(transformationMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locations.getValue(trMatrix), transformationMatrix)
    }

    override fun loadProjectionMatrix(projectionMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locations.getValue(prjMatrix), projectionMatrix)
    }

    override fun loadViewMatrix(viewMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locations.getValue(no.sigurof.grajuny.shader.shaders.viewMatrix), viewMatrix)
    }

    override fun loadSpecularValues(damper: Float, reflectivity: Float) {
        ShaderManager.loadFloat(locations.getValue(shineDamper), damper)
        ShaderManager.loadFloat(locations.getValue(no.sigurof.grajuny.shader.shaders.reflectivity), reflectivity)
    }

    override fun loadCameraPosition(cameraPosition: Vector3f) {
        ShaderManager.loadVector3(locations.getValue(cameraPos), cameraPosition)
    }

    override fun loadColor(color: Vector3f) {
        ShaderManager.loadVector3(locations.getValue(no.sigurof.grajuny.shader.shaders.color), color);
    }

    override fun loadLight(lightSource: LightSource) {
        ShaderManager.loadVector3(locations.getValue(lightPos), lightSource.position)
        ShaderManager.loadVector3(locations.getValue(lightCol), lightSource.color)
        ShaderManager.loadFloat(locations.getValue(ambientStrength), lightSource.ambient)
    }

    override fun loadUseTexture(useTexture: Boolean) {
        ShaderManager.loadBoolean(locations.getValue(no.sigurof.grajuny.shader.shaders.useTexture), useTexture)
    }

    fun loadUseSpecular(useSpecular: Boolean) {
        ShaderManager.loadBoolean(locations.getValue(useSpec), useSpecular)
    }

    fun loadUseDiffuse(useDiffuse: Boolean) {
        ShaderManager.loadBoolean(locations.getValue(no.sigurof.grajuny.shader.shaders.useDiffuse), useDiffuse)
    }

}