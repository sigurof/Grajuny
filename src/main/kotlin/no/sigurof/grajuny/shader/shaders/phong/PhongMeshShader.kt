package no.sigurof.grajuny.shader.shaders.phong

import no.sigurof.grajuny.light.phong.PointLight
import no.sigurof.grajuny.resource.material.PhongMaterial
import no.sigurof.grajuny.shader.Shader
import no.sigurof.grajuny.shader.ShaderManager
import no.sigurof.grajuny.shader.interfaces.CameraShader
import no.sigurof.grajuny.shader.interfaces.LightShader
import no.sigurof.grajuny.shader.interfaces.Shader3D
import org.joml.Matrix4f
import org.joml.Vector3f

object PhongMeshShader : Shader(
    vtxSource = "/shader/mesh/phong/vertex.shader",
    frgSource = "/shader/mesh/phong/fragment.shader",
    attributes = listOf(
        0 to "position",
        1 to "textureCoords",
        2 to "normal"
    ),
    uniforms = listOf(
        TR_MATRIX,
        PRJ_MATRIX,
        VIEW_MATRIX,
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
        MATERIAL_SHININESS
    )
),
    LightShader,
    Shader3D,
    CameraShader {

    override fun loadTransformationMatrix(transformationMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locations.getValue(TR_MATRIX), transformationMatrix)
    }

    override fun loadProjectionMatrix(projectionMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locations.getValue(PRJ_MATRIX), projectionMatrix)
    }

    override fun loadViewMatrix(viewMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locations.getValue(VIEW_MATRIX), viewMatrix)
    }

    override fun loadCameraPosition(cameraPosition: Vector3f) {
        ShaderManager.loadVector3(locations.getValue(CAMERA_POS), cameraPosition)
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