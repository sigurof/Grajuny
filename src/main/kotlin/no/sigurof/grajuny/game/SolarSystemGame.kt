package no.sigurof.grajuny.game

import no.sigurof.grajuny.camera.Camera
import no.sigurof.grajuny.camera.CameraManager
import no.sigurof.grajuny.camera.impl.SpaceShipCamera
import no.sigurof.grajuny.color.BLACK
import no.sigurof.grajuny.color.BLUE
import no.sigurof.grajuny.color.GRAY
import no.sigurof.grajuny.color.YELLOW
import no.sigurof.grajuny.components.MeshRenderer
import no.sigurof.grajuny.components.SphereBillboardRenderer
import no.sigurof.grajuny.components.TraceRenderer
import no.sigurof.grajuny.light.LightManager
import no.sigurof.grajuny.light.phong.PointLight
import no.sigurof.grajuny.node.GameComponent
import no.sigurof.grajuny.node.GameObject
import no.sigurof.grajuny.postprocessing.PostProcessingEffect
import no.sigurof.grajuny.postprocessing.PostProcessingManager
import no.sigurof.grajuny.resource.material.PhongMaterial
import no.sigurof.grajuny.resource.texture.TextureManager
import no.sigurof.grajuny.utils.CyclicCounter
import no.sigurof.grajuny.utils.ORIGIN
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f
import org.lwjgl.glfw.GLFW
import kotlin.math.cos
import kotlin.math.sin

class SolarSystemGame(
    window: Long
) : Game(
    window = window,
    background = Vector4f(0f, 0f, 0.5f, 1f)
) {
    private var moonObj: GameObject
    private var earthMoonObject: GameObject
    private var solarSystem: GameObject
    private val cameras: List<Camera>
    private val cameraIndex: CyclicCounter

    private val sun: GameComponent
    private val moonRadius = 8f
    private val earthRadius = 100f

    init {
        PostProcessingManager.addEffect(PostProcessingEffect())
        LightManager.LIGHT_SOURCES.addAll(
            listOf(
                PointLight(
                    position = ORIGIN,
                    constant = 1f,
                    linear = 0.0014f,
                    quadratic = 0.00007f,
                    ambient = Vector3f(0.2f, 0.2f, 0.2f),
                    diffuse = Vector3f(0.8f, 0.8f, 0.8f),
                    specular = Vector3f(1.5f, 1.5f, 1.5f)
                )
            )
        )
        val sunPos = Vector3f(1f, 0f, 0f)

        val pureYellow = PhongMaterial(ambient = YELLOW, diffuse = BLACK, specular = BLACK)
        val diffuseYellow = PhongMaterial(ambient = YELLOW, specular = BLACK)
        val gray = PhongMaterial(ambient = GRAY)
        sun = SphereBillboardRenderer(
            material = pureYellow,
            radius = 5f,
            position = Vector3f(0f, 0f, 0f)
        )
        val sunFriend = MeshRenderer(
            material = diffuseYellow,
            meshName = "torus"
        )
        val earth = SphereBillboardRenderer(
            material = PhongMaterial(ambient = TextureManager.get("earth512")),
            radius = 1f,
            position = Vector3f(0f, 0f, 0f)
        )
        val moon = SphereBillboardRenderer(
            material = gray,
            radius = 0.5f,
            position = Vector3f(0f, 0f, 0f)
        )
        moonObj = GameObject.withComponent(moon).at(Vector3f(moonRadius, 0f, 0f)).build()
        val sunFriendObj = GameObject.withComponent(sunFriend).at(Vector3f(10f, 0f, 0f)).build()
        val earthObj = GameObject.withComponent(earth).at(Vector3f(0f, 0f, 0f)).build()
        earthMoonObject = GameObject.withChildren(earthObj, moonObj).at(Vector3f(earthRadius, 0f, 0f)).build()
        val sunObject = GameObject.withComponent(sun).at(sunPos).build()
        solarSystem = GameObject.withChildren(sunObject, earthMoonObject).build()
        root.addChild(solarSystem)
        root.addChild(sunFriendObj)
        cameras = listOf(
            SpaceShipCamera(
                parent = moonObj,
                window = window,
                lookAt = ORIGIN,
                at = Vector3f(0f, 2f, -3f),
                upDir = Vector3f(0f, 1f, 0f)
            ),
            SpaceShipCamera(
                window = window,
                lookAt = ORIGIN,
                at = Vector3f(0f, 2f, -3f),
                upDir = Vector3f(0f, 1f, 0f)
            ),
            SpaceShipCamera(
                parent = earthObj,
                window = window,
                lookAt = ORIGIN,
                at = Vector3f(0f, 2f, -3f),
                upDir = Vector3f(0f, 1f, 0f)
            )

        )
        TraceRenderer.Builder(
            color = GRAY, numberOfPoints = 500
        )
            .attachTo(moonObj)
            .build()
        TraceRenderer.Builder(
            color = BLUE, numberOfPoints = 500
        )
            .attachTo(earthObj)
            .build()
        cameraIndex = CyclicCounter.exclusiveMax(cameras.size)
    }

    private var angle = 0f

    override fun onUpdate() {
        val elapsedSeconds = elapsedMs / 1000f
        val deltaAngle = deltaTime / 30000f

        earthMoonObject.transform =
            Matrix4f().translation(Vector3f(earthRadius * cos(angle), 0f, earthRadius * sin(angle)))
        moonObj.transform =
            Matrix4f().translation(Vector3f(moonRadius * cos(12 * angle), 0f, moonRadius * sin(12 * angle)))
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_C) == GLFW.GLFW_TRUE) {
            cameraIndex.incrementByOne()
            CameraManager.activeCamera = cameras[cameraIndex.current]
        }
        angle += deltaAngle

    }
}
