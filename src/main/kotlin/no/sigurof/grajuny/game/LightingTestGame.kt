package no.sigurof.grajuny.game

import no.sigurof.grajuny.camera.Camera
import no.sigurof.grajuny.camera.impl.SpaceShipCamera
import no.sigurof.grajuny.components.MeshRenderer
import no.sigurof.grajuny.light.LightManager
import no.sigurof.grajuny.light.PhongLight
import no.sigurof.grajuny.node.GameObject
import no.sigurof.grajuny.postprocessing.PostProcessingEffect
import no.sigurof.grajuny.postprocessing.PostProcessingManager
import no.sigurof.grajuny.resource.material.PhongMaterial
import no.sigurof.grajuny.resource.texture.TextureManager
import no.sigurof.grajuny.shader.shaders.PhongMeshShader2
import no.sigurof.grajuny.utils.CyclicCounter
import no.sigurof.grajuny.utils.ORIGIN
import no.sigurof.grajuny.utils.plus
import org.joml.Quaternionf
import org.joml.Vector3f
import org.joml.Vector4f

class LightingTestGame(
    window: Long
) : Game(
    window = window,
    background = Vector4f(0f, 0f, 0.5f, 1f)
) {
    private var sunFriendObj: GameObject
    private var sphereObj: GameObject

    private val cameras: List<Camera>
    private val cameraIndex: CyclicCounter

    init {
        PostProcessingManager.addEffect(PostProcessingEffect())
        LightManager.LIGHT_SOURCES.addAll(
            listOf(
                PhongLight(
                    position = Vector3f(0f, 10f, -2f),
                    ambient = Vector3f(1f, 1f, 1f),
                    diffuse = Vector3f(1f, 1f, 1f),
                    specular = Vector3f(1f, 1f, 1f)
                )
            )
        )
        val sunFriend = MeshRenderer(
            material = PhongMaterial(
                ambient = Vector3f(),
                diffuse = TextureManager.get("container-diff"),
                specular = Vector3f(1f, 1f, 1f),
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
        root.addChild(sunFriendObj)
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

    }
}
