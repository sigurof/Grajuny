package no.sigurof.grajuny.game

import no.sigurof.grajuny.camera.Camera
import no.sigurof.grajuny.camera.CameraManager
import no.sigurof.grajuny.camera.impl.SpaceShipCamera
import no.sigurof.grajuny.color.BLUE
import no.sigurof.grajuny.color.GRAY
import no.sigurof.grajuny.color.YELLOW
import no.sigurof.grajuny.components.SphereBillboardRenderer
import no.sigurof.grajuny.components.TraceRenderer
import no.sigurof.grajuny.light.LightSource
import no.sigurof.grajuny.node.GameComponent
import no.sigurof.grajuny.node.GameObject
import no.sigurof.grajuny.resource.material.RegularMaterial
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

    //    private val baryCenter: GameObject
    private val sun: GameComponent

    //    private val earth: GameComponent
//    private val moon: GameComponent
    val sunRadius = 0.1f
    val moonRadius = 8f
    val earthRadius = 100f

    init {
        val sunPos = Vector3f(1f, 0f, 0f)
        LightSource.Builder().position(sunPos).build()
        val cameraPos = Vector3f(0f, 10f, 20f)
        cameras = listOf(
            SpaceShipCamera(
                window = window,
                transform = Matrix4f()
                    .translate(Vector3f(0f, 2f, -3f))
                    .lookAt(Vector3f(0f, 3f, -3f), ORIGIN, Vector3f(0f, 1f, 0f))
            )
        )
        val pureYellow = RegularMaterial(YELLOW, diffuse = false, specular = false)
        val diffuseYellow = RegularMaterial(YELLOW, diffuse = true, specular = false)
        val blueShiny = RegularMaterial(BLUE, 10f, 100f)
        val gray = RegularMaterial(GRAY, 1f, 100f)
        val earthMoonPos = Vector3f(15f, 0f, 0f)
        sun = SphereBillboardRenderer(
            material = pureYellow,
            radius = 5f,
            position = Vector3f(0f, 0f, 0f)
        )
        val earth = SphereBillboardRenderer(
            material = blueShiny,
            radius = 1f,
            position = Vector3f(0f, 0f, 0f)
        )
        val moon = SphereBillboardRenderer(
            material = gray,
            radius = 0.5f,
            position = Vector3f(0f, 0f, 0f)
        )
        moonObj = GameObject.withComponent(moon).at(Vector3f(moonRadius, 0f, 0f)).build()
        val earthObj = GameObject.withComponent(earth).at(Vector3f(0f, 0f, 0f)).build()
        earthMoonObject = GameObject.withChildren(earthObj, moonObj).at(Vector3f(earthRadius, 0f, 0f)).build()
        val sunObject = GameObject.withComponent(sun).at(sunPos).build()
        solarSystem = GameObject.withChildren(sunObject, earthMoonObject).build()
        root.addChild(solarSystem)
        (cameras[0] as SpaceShipCamera).parent = moonObj
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
        cameraIndex  = CyclicCounter.exclusiveMax(cameras.size)
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
