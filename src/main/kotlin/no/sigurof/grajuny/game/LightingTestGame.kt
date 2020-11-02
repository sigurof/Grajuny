package no.sigurof.grajuny.game

import no.sigurof.grajuny.camera.Camera
import no.sigurof.grajuny.camera.impl.SpaceShipCamera
import no.sigurof.grajuny.color.WHITE
import no.sigurof.grajuny.components.MeshRenderer
import no.sigurof.grajuny.components.SphereBillboardRenderer
import no.sigurof.grajuny.light.LightManager
import no.sigurof.grajuny.light.phong.PointLight
import no.sigurof.grajuny.node.GameObject
import no.sigurof.grajuny.postprocessing.PostProcessingEffect
import no.sigurof.grajuny.postprocessing.PostProcessingManager
import no.sigurof.grajuny.resource.material.PhongMaterial
import no.sigurof.grajuny.resource.texture.TextureManager
import no.sigurof.grajuny.shader.shaders.PhongBillboardShader
import no.sigurof.grajuny.shader.shaders.PhongMeshShader2
import no.sigurof.grajuny.utils.CyclicCounter
import no.sigurof.grajuny.utils.ORIGIN
import no.sigurof.grajuny.utils.plus
import org.joml.Quaternionf
import org.joml.Vector3f
import org.joml.Vector4f
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.GLFW_TRUE
import org.lwjgl.glfw.GLFW.glfwGetKey

class LightingTestGame(
    window: Long
) : Game(
    window = window,
    background = Vector4f(0f, 0f, 0.5f, 1f)
) {
    private var sunFriendObj: GameObject
    private var sunFriendObj2: GameObject
    private var sunFriendObj3: GameObject
    private var sphereObj: GameObject

    private val cameras: List<Camera>
    private val cameraIndex: CyclicCounter

    init {
        PostProcessingManager.addEffect(PostProcessingEffect())
        LightManager.LIGHT_SOURCES.addAll(
            listOf(
                PointLight(
                    position = Vector3f(0f, 10f, -2f),
                    constant = 1f,
                    linear = 0.014f,
                    quadratic = 0.0007f,
                    ambient = Vector3f(0.2f, 0.2f, 0.2f),
                    diffuse = Vector3f(0.8f, 0.8f, 0.8f),
                    specular = Vector3f(1.5f, 1.5f, 1.5f)
                )
            )
        )
        val earth = SphereBillboardRenderer(
            material = PhongMaterial(
                ambient = TextureManager.get("earth512"),
                diffuse = TextureManager.get("earth512"),
                specular = TextureManager.get1x1Texture(WHITE),
                shine = 0.1f
            ),
            shadersToUse = listOf(PhongBillboardShader),
            position = Vector3f(),
            radius = 2f
        )
        val sunFriend = MeshRenderer(
            material = PhongMaterial(
                ambient = TextureManager.get1x1Texture(Vector3f(0f, 0f, 0f)),
                diffuse = TextureManager.get("container-diff"),
                specular = TextureManager.get1x1Texture(Vector3f(1f, 1f, 1f)),
                shine = 10f
            ),
            meshName = "cube",
            shadersToUse = listOf(PhongMeshShader2)
        )
        val sunFriend2 = MeshRenderer(
            material = PhongMaterial(
                ambient = TextureManager.get1x1Texture(Vector3f(0f, 0f, 0f)),
                diffuse = TextureManager.get("container-diff"),
                specular = TextureManager.get("container-spec"),
                shine = 10f
            ),
            meshName = "cube",
            shadersToUse = listOf(PhongMeshShader2)
        )
        val sunFriend3 = MeshRenderer(
            material = PhongMaterial(
                ambient = TextureManager.get("container-diff"),
                diffuse = TextureManager.get("container-diff"),
                specular = TextureManager.get("container-spec"),
                shine = 10f
            ),
            meshName = "cube",
            shadersToUse = listOf(PhongMeshShader2)
        )
        val sphere = MeshRenderer(
            material = PhongMaterial.emerald,
            meshName = "sphere",
            shadersToUse = listOf(PhongMeshShader2)
        )
        sphereObj = GameObject.withChild(GameObject.withComponent(sphere).at(Vector3f(0f, -10f, -0.6f)).build())
            .at(Vector3f(0f, 0f, -4f)).build()
        val sunFriendPos = ORIGIN
        sunFriendObj = GameObject.withComponent(sunFriend).at(sunFriendPos).build()
        sunFriendObj2 = GameObject.withComponent(sunFriend2).at(Vector3f(0f, 1f, -3f)).build()
        sunFriendObj3 = GameObject.withComponent(sunFriend3).at(Vector3f(0f, 1f, -6f)).build()
        root.addChild(sunFriendObj)
        root.addChild(sunFriendObj2)
        root.addChild(sunFriendObj3)
        val earthObj = GameObject.withComponent(earth).at(Vector3f(1f, 1f, 1f)).build()
        root.addChild(earthObj)
        root.addChild(sphereObj)
        cameras = listOf(
            SpaceShipCamera(
                parent = root,
                window = window,
                lookAt = sunFriendPos,
                at = sunFriendPos.plus(Vector3f(8f, 0f, 0f)),
                upDir = Vector3f(0f, 1f, 0f)
            )
        )
        cameraIndex = CyclicCounter.exclusiveMax(cameras.size)
    }

    private var angle = 0f

    override fun onUpdate() {
        val elapsedSeconds = elapsedMs / 1000f
        val deltaAngle = deltaTime / 3000f
        angle += deltaAngle
        sphereObj.transform.rotate(Quaternionf().rotateAxis(deltaAngle, Vector3f(0f, 1f, 0f)))
        if (glfwGetKey(window, GLFW.GLFW_KEY_R) == GLFW_TRUE) {
            sunFriendObj3.transform.translate(Vector3f(0f, 10 * deltaAngle, 0f))
        }
        if (glfwGetKey(window, GLFW.GLFW_KEY_C) == GLFW_TRUE) {
            sunFriendObj3.transform.translate(Vector3f(0f, -10 * deltaAngle, 0f))
        }

    }
}
