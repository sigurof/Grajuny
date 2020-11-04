package no.sigurof.grajuny.shader.shaders.phong

import no.sigurof.grajuny.light.Light
import no.sigurof.grajuny.light.phong.PointLight
import no.sigurof.grajuny.resource.material.PhongMaterial
import no.sigurof.grajuny.shader.Shader
import no.sigurof.grajuny.shader.ShaderManager
import no.sigurof.grajuny.shader.interfaces.BillboardShader
import no.sigurof.grajuny.shader.interfaces.CameraShader
import no.sigurof.grajuny.shader.interfaces.LightShader
import org.joml.Matrix4f
import org.joml.Vector3f

const val SPHERE_RADIUS = "sphereRadius"
const val SPHERE_CENTER = "sphereCenter"
const val NUMBER_OF_POINT_LIGHTS = "numberOfPointLights"
const val MAX_NR_POINT_LIGHTS = 10

object PhongBillboardShader : Shader(
    vtxSource = "/shader/billboard/phong/vertex.shader",
    frgSource = "/shader/billboard/phong/fragment.shader",
    attributes = emptyList(),
    uniforms = listOf(
        PRJ_MATRIX,
        VIEW_MATRIX,
        SPHERE_RADIUS,
        SPHERE_CENTER,
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
    CameraShader,
    LightShader,
    BillboardShader {

    override fun loadProjectionMatrix(projectionMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locations.getValue("prjMatrix"), projectionMatrix)
    }

    override fun loadViewMatrix(viewMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locations.getValue("viewMatrix"), viewMatrix)
    }

    override fun loadCameraPosition(cameraPosition: Vector3f) {
        ShaderManager.loadVector3(locations.getValue(CAMERA_POS), cameraPosition)
    }

    override fun loadSphereCenter(sphereCenter: Vector3f) {
        ShaderManager.loadVector3(locations.getValue(SPHERE_CENTER), sphereCenter)
    }

    override fun loadSphereRadius(radius: Float) {
        ShaderManager.loadFloat(locations.getValue(SPHERE_RADIUS), radius)
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