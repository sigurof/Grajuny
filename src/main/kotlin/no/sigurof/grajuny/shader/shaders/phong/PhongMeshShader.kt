package no.sigurof.grajuny.shader.shaders.phong

import no.sigurof.grajuny.light.Light
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
        MATERIAL_AMBIENT,
        MATERIAL_DIFFUSE,
        MATERIAL_SPECULAR,
        MATERIAL_SHININESS,
        NUMBER_OF_POINT_LIGHTS
    ).plus(
        (0 until MAX_NR_POINT_LIGHTS).flatMap { i ->
            listOf(
                "lights[$i].position",
                "lights[$i].constant",
                "lights[$i].linear",
                "lights[$i].quadratic",
                "lights[$i].ambient",
                "lights[$i].diffuse",
                "lights[$i].specular"
            )
        }
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

    fun render(pointLight: PointLight, index: Int) {
        ShaderManager.loadVector3(locations.getValue("lights[$index].position"), pointLight.position)
        ShaderManager.loadFloat(locations.getValue("lights[$index].constant"), pointLight.constant)
        ShaderManager.loadFloat(locations.getValue("lights[$index].linear"), pointLight.linear)
        ShaderManager.loadFloat(locations.getValue("lights[$index].quadratic"), pointLight.quadratic)
        ShaderManager.loadVector3(locations.getValue("lights[$index].ambient"), pointLight.ambient)
        ShaderManager.loadVector3(locations.getValue("lights[$index].diffuse"), pointLight.diffuse)
        ShaderManager.loadVector3(locations.getValue("lights[$index].specular"), pointLight.specular)
    }

    override fun render(lights: MutableList<Light>) {
        val pointLights = lights.filterIsInstance<PointLight>()
        ShaderManager.loadInt(locations.getValue(NUMBER_OF_POINT_LIGHTS), pointLights.count())
        pointLights
            .take(MAX_NR_POINT_LIGHTS)
            .forEachIndexed { index, pointLight -> render(pointLight, index) }
    }

}