package no.sigurof.grajuny.shader.shaders

import no.sigurof.grajuny.light.phong.PointLight
import no.sigurof.grajuny.resource.material.PhongMaterial
import no.sigurof.grajuny.shader.Shader
import no.sigurof.grajuny.shader.ShaderManager
import no.sigurof.grajuny.shader.interfaces.BillboardShader
import no.sigurof.grajuny.shader.interfaces.CameraShader
import no.sigurof.grajuny.shader.interfaces.LightShader
import no.sigurof.grajuny.shader.shaders.phong.LIGHT_AMBIENT
import no.sigurof.grajuny.shader.shaders.phong.LIGHT_CONSTANT
import no.sigurof.grajuny.shader.shaders.phong.LIGHT_DIFFUSE
import no.sigurof.grajuny.shader.shaders.phong.LIGHT_LINEAR
import no.sigurof.grajuny.shader.shaders.phong.LIGHT_POSITION
import no.sigurof.grajuny.shader.shaders.phong.LIGHT_QUADRATIC
import no.sigurof.grajuny.shader.shaders.phong.LIGHT_SPECULAR
import no.sigurof.grajuny.shader.shaders.phong.MATERIAL_AMBIENT
import no.sigurof.grajuny.shader.shaders.phong.MATERIAL_DIFFUSE
import no.sigurof.grajuny.shader.shaders.phong.MATERIAL_SHININESS
import no.sigurof.grajuny.shader.shaders.phong.MATERIAL_SPECULAR
import org.joml.Matrix4f
import org.joml.Vector3f

const val CAMERA_POS = "cameraPos"

object PhongBillboardShader : Shader(
    vtxSource = "/shader/billboard/phong/vertex.shader",
    frgSource = "/shader/billboard/phong/fragment.shader",
    attributes = emptyList(),
    uniforms = listOf(
        "prjMatrix",
        "viewMatrix",
        "sphereRadius",
        "sphereCenter",
        CAMERA_POS,
        LIGHT_POSITION,
        LIGHT_AMBIENT,
        LIGHT_DIFFUSE,
        LIGHT_SPECULAR,
        LIGHT_CONSTANT,
        LIGHT_LINEAR,
        LIGHT_QUADRATIC,
        MATERIAL_AMBIENT,
        MATERIAL_DIFFUSE,
        MATERIAL_SPECULAR,
        MATERIAL_SHININESS,
        "frPrjMatrix",
        "frViewMatrix",
        "frSphereRadius",
        "frCameraPos",
        "frSphereCenter"
    )
),
    CameraShader,
    LightShader,
    BillboardShader {

    override fun loadProjectionMatrix(projectionMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locations.getValue("prjMatrix"), projectionMatrix)
        ShaderManager.loadMatrix(locations.getValue("frPrjMatrix"), projectionMatrix)
    }

    override fun loadViewMatrix(viewMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locations.getValue("viewMatrix"), viewMatrix)
        ShaderManager.loadMatrix(locations.getValue("frViewMatrix"), viewMatrix)
    }

    override fun loadCameraPosition(cameraPosition: Vector3f) {
        ShaderManager.loadVector3(locations.getValue(CAMERA_POS), cameraPosition)
        ShaderManager.loadVector3(locations.getValue("frCameraPos"), cameraPosition)
    }

    fun loadLight(light: PointLight) {
        ShaderManager.loadVector3(locations.getValue(LIGHT_POSITION), light.position)
        ShaderManager.loadFloat(locations.getValue(LIGHT_CONSTANT), light.constant)
        ShaderManager.loadFloat(locations.getValue(LIGHT_LINEAR), light.linear)
        ShaderManager.loadFloat(locations.getValue(LIGHT_QUADRATIC), light.quadratic)
        ShaderManager.loadVector3(locations.getValue(LIGHT_AMBIENT), light.ambient)
        ShaderManager.loadVector3(locations.getValue(LIGHT_DIFFUSE), light.diffuse)
        ShaderManager.loadVector3(locations.getValue(LIGHT_SPECULAR), light.specular)
    }

    override fun loadSphereCenter(sphereCenter: Vector3f) {
        ShaderManager.loadVector3(locations.getValue("sphereCenter"), sphereCenter)
        ShaderManager.loadVector3(locations.getValue("frSphereCenter"), sphereCenter)
    }

    override fun loadSphereRadius(radius: Float) {
        ShaderManager.loadFloat(locations.getValue("sphereRadius"), radius)
        ShaderManager.loadFloat(locations.getValue("frSphereRadius"), radius)
    }

    fun loadMaterial(phongMaterial: PhongMaterial) {
        ShaderManager.loadInt(
            locations.getValue(MATERIAL_AMBIENT),
            phongMaterial.indexToGlTexture["ambient"]?.first ?: error("")
        )
        ShaderManager.loadInt(
            locations.getValue(MATERIAL_DIFFUSE),
            phongMaterial.indexToGlTexture["diffuse"]?.first ?: error("")
        )
        ShaderManager.loadInt(
            locations.getValue(MATERIAL_SPECULAR),
            phongMaterial.indexToGlTexture["specular"]?.first ?: error("")
        )
        ShaderManager.loadFloat(locations.getValue(MATERIAL_SHININESS), phongMaterial.shininess)
    }
}