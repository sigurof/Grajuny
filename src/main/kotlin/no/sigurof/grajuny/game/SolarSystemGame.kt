package no.sigurof.grajuny.game

import no.sigurof.grajuny.camera.Camera
import no.sigurof.grajuny.color.BLUE
import no.sigurof.grajuny.color.GRAY
import no.sigurof.grajuny.color.YELLOW
import no.sigurof.grajuny.components.MeshRenderer
import no.sigurof.grajuny.components.SphereBillboardRenderer
import no.sigurof.grajuny.components.TraceRenderer
import no.sigurof.grajuny.light.LightSource
import no.sigurof.grajuny.node.GameComponent
import no.sigurof.grajuny.node.GameObject
import no.sigurof.grajuny.resource.material.RegularMaterial
import no.sigurof.grajuny.utils.ORIGIN
import org.joml.Vector3f
import org.joml.Vector4f

class SolarSystemGame(
    window: Long
) : Game(
    window = window,
    background = Vector4f(0f, 0f, 0.5f, 1f)
) {
    private var earthMoonObject: GameObject
    private var solarSystem: GameObject
    override val camera: Camera

    //    private val baryCenter: GameObject
    private val sun: GameComponent

    //    private val earth: GameComponent
//    private val moon: GameComponent

    init {
        val sunPos = Vector3f(1f, 0f, 0f)
        LightSource.Builder().position(sunPos).build()
        val cameraPos = Vector3f(0f, 0f, 20f)
        camera = Camera.Builder()
            .at(cameraPos)
            .lookingAt(ORIGIN)
            .capturingMouseInput(window)
            .build()
        val pureYellow = RegularMaterial(YELLOW, diffuse = false, specular = false)
        val diffuseYellow = RegularMaterial(YELLOW, diffuse = true, specular = false)
        val blueShiny = RegularMaterial(BLUE, 10f, 100f)
        val gray = RegularMaterial(GRAY, 1f, 100f)
        val earthMoonPos = Vector3f(15f, 0f, 0f)
//        sun = SphereBillboardRenderer(
//            material = yellowShiny,
//            radius = 5f,
//            position = Vector3f(0f, 0f, 0f)
//        )
        sun = MeshRenderer(
            meshName = "torus",
            material = diffuseYellow
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
        val moonObj = GameObject.withComponent(moon).at(Vector3f(7f, 0f, 0f)).build()
        val earthObj = GameObject.withComponent(earth).at(Vector3f(1f, 0f, 0f)).build()
        earthMoonObject = GameObject.withChildren(earthObj, moonObj).at(earthMoonPos).build()
        val sunObject = GameObject.withComponent(sun).at(sunPos).build()
        solarSystem = GameObject.withChildren(sunObject, earthMoonObject).build()
        root.addChild(solarSystem)
        TraceRenderer.Builder(
            color = GRAY, numberOfPoints = 50
        )
            .attachTo(moonObj)
            .build()
        TraceRenderer.Builder(
            color = BLUE, numberOfPoints = 50
        )
            .attachTo(earthObj)
            .build()
    }

    override fun onUpdate() {
        val elapsedSeconds = elapsedMs / 1000f
        val angle = deltaTime / 300f
        solarSystem.transform.rotate(angle / 12f, Vector3f(0f, 1f, 0f))
        earthMoonObject.transform.rotate(angle, Vector3f(0f, 1f, 0f))

    }
}
