package no.sigurof.grajuny.restructuring

import no.sigurof.grajuny.resource.TextureManager
import no.sigurof.grajuny.restructuring.node.GameObject
import no.sigurof.grajuny.utils.BLUE
import org.joml.Vector3f
import org.joml.Vector4f
import kotlin.math.absoluteValue
import kotlin.math.sin

class TTestGame(
    window: Long
) : TGame(
    window = window,
    background = Vector4f(1f, 0.5f, 1f, 1f)
) {
    private val cube: GameObject
    override val camera: TCamera
    private val torus: GameComponent

    init {
        val texture = TTexture(TextureManager.get("stall"))
        val torusMesh = TMeshResource(TMeshManager.getMesh("torus"), listOf(0, 1, 2))
        val cubeMesh = TMeshResource(TMeshManager.getMesh("cube"), listOf(0, 1, 2))
        val blueShiny = TMaterial(BLUE, 1f, 100f)
        cube = GameObject.withChild(
            GameObject.withComponent(MeshRenderer(cubeMesh, texture, blueShiny))
                .at(Vector3f(9f, 0f, 9f))
                .build()
        ).build()
        root.addChild(
            cube
        )
        torus = MeshRenderer(torusMesh, texture, blueShiny)
        root.addGameComponent(torus)
        // Make light
        LightSource.Builder().position(Vector3f(0f, 100f, 40f)).build()
        camera = TCamera.Builder()
            .at(Vector3f(10f, 10f, 10f))
            .lookingAt(root.transform.getColumn(3, Vector3f()))
            .capturingMouseInput(window)
            .build()
    }

    override fun onUpdate() {
        val elapsedSeconds = elapsedMs / 1000f
        background = Vector4f(sin(elapsedSeconds).absoluteValue, 0.5f, 0.5f, 1f)
        val angle = deltaTime / 1000f
        torus.transform.rotate(angle, Vector3f(0f, 1f, 0f))
        cube.rotate(angle, Vector3f(1f, 0f, 0f))
    }

}