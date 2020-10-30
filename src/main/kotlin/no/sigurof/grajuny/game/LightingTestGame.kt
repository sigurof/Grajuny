package no.sigurof.grajuny.game

import no.sigurof.grajuny.camera.Camera
import no.sigurof.grajuny.camera.impl.SpaceShipCamera
import no.sigurof.grajuny.color.GRAY
import no.sigurof.grajuny.color.WHITE
import no.sigurof.grajuny.color.YELLOW
import no.sigurof.grajuny.components.MeshRenderer
import no.sigurof.grajuny.components.SphereBillboardRenderer
import no.sigurof.grajuny.light.LightManager
import no.sigurof.grajuny.light.PhongLight
import no.sigurof.grajuny.node.GameComponent
import no.sigurof.grajuny.node.GameObject
import no.sigurof.grajuny.postprocessing.PostProcessingEffect
import no.sigurof.grajuny.postprocessing.PostProcessingManager
import no.sigurof.grajuny.resource.material.PhongMaterial
import no.sigurof.grajuny.resource.material.RegularMaterial
import no.sigurof.grajuny.resource.texture.TextureRenderer
import no.sigurof.grajuny.shader.shaders.PhongMeshShader2
import no.sigurof.grajuny.utils.CyclicCounter
import no.sigurof.grajuny.utils.ORIGIN
import org.joml.Quaternionf
import org.joml.Vector3f
import org.joml.Vector4f

class LightingTestGame(
    window: Long
) : Game(
    window = window,
    background = Vector4f(0f, 0f, 0.5f, 1f)
) {
    private var sphereObj: GameObject
    private var moonObj: GameObject
    private var earthMoonObject: GameObject
    private var solarSystem: GameObject
    private val cameras: List<Camera>
    private val cameraIndex: CyclicCounter

    private val sun: GameComponent

    init {
        PostProcessingManager.addEffect(PostProcessingEffect())
        LightManager.LIGHT_SOURCES.addAll(
            listOf(
                PhongLight(
                    position = Vector3f(0f, 0f, 0f),
                    ambient = Vector3f(0.2f, 0.2f, 0.2f),
                    diffuse = Vector3f(0.75f, 0.75f, 0.75f),
                    specular = Vector3f(1.5f, 1.5f, 1.5f)
                )
            )
        )
        val pureYellow = RegularMaterial(YELLOW, diffuse = false, specular = false)
        val gray = RegularMaterial(GRAY, 0.5f, 32f)
        sun = SphereBillboardRenderer(
            material = pureYellow,
            radius = 1f,
            position = Vector3f(0f, 0f, 0f)
        )
        val sunFriend = MeshRenderer(
            material = PhongMaterial.gold,
            meshName = "cube",
            shadersToUse = listOf(PhongMeshShader2)
        )
        val sphere = MeshRenderer(
            material = PhongMaterial.copper,
            meshName = "sphere",
            shadersToUse = listOf(PhongMeshShader2)
        )
        val earth = SphereBillboardRenderer(
            material = RegularMaterial(WHITE, 0.5f, 32f),
            radius = 1f,
            position = Vector3f(0f, 0f, 0f),
            textureRenderer = TextureRenderer.fromName("earth512")
        )
        val moon = SphereBillboardRenderer(
            material = gray,
            radius = 1f,
            position = Vector3f(0f, 0f, 0f)
        )
        moonObj = GameObject.withComponent(moon).at(Vector3f(0f, 0f, 0f)).build()

        sphereObj = GameObject.withChild(GameObject.withComponent(sphere).at(Vector3f(0f, 0f, 0f)).build())
            .at(Vector3f(0f, 0f, -4f)).build()
//        sphereObj.children[0].transform.scale(2f, 1f, 1f)
        val sunFriendObj = GameObject.withComponent(sunFriend).at(Vector3f(2f, -2f, 0f)).build()
//        sunFriendObj.transform.scale(1f, 2f, 1f)
        val earthObj = GameObject.withComponent(earth).at(Vector3f(0f, 0f, 2f)).build()
        earthMoonObject = GameObject.withChildren(earthObj, moonObj).at(Vector3f(2f, 0f, 2f)).build()
        val sunObject = GameObject.withComponent(sun).at(Vector3f(0f, 4f, 0f)).build()
        solarSystem = GameObject.withChildren(sunObject, earthMoonObject).build()
        root.addChild(solarSystem)
        root.addChild(sunFriendObj)
        root.addChild(sphereObj)
        cameras = listOf(
            SpaceShipCamera(
                parent = moonObj,
                window = window,
                lookAt = ORIGIN,
                at = Vector3f(5f, 5f, 5f),
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
