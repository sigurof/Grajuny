package no.sigurof.grajuny.game

import no.sigurof.grajuny.camera.TCamera
import no.sigurof.grajuny.components.MeshRenderer
import no.sigurof.grajuny.components.SphereBillboardRenderer
import no.sigurof.grajuny.light.LightSource
import no.sigurof.grajuny.node.GameComponent
import no.sigurof.grajuny.node.GameObject
import no.sigurof.grajuny.resource.TMaterial
import no.sigurof.grajuny.resource.TMeshResource
import no.sigurof.grajuny.resource.TTexture
import no.sigurof.grajuny.resource.TextureManager
import no.sigurof.grajuny.resource.mesh.TMeshManager
import no.sigurof.grajuny.utils.BLUE
import no.sigurof.grajuny.utils.RED
import no.sigurof.grajuny.utils.YELLOW
import org.joml.Matrix4f
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
    private val sphere: GameComponent
    private val sphere2: GameComponent
    private val dragon: GameObject

    init {
        val texture = TTexture(TextureManager.get("stall"))
        val earthTex = TTexture(TextureManager.get("earth512"))
        val torusMesh = TMeshResource(TMeshManager.getMesh("torus"), listOf(0, 1, 2))
        val cubeMesh = TMeshResource(TMeshManager.getMesh("cube"), listOf(0, 1, 2))
        val blueShiny = TMaterial(BLUE, 1f, 100f)
        val redShiny = TMaterial(RED, 1f, 100f)
        val yellowShiny = TMaterial(YELLOW, 10f, 100f)
        cube = GameObject.withChild(
            GameObject.withComponent(
                MeshRenderer(cubeMesh, texture, blueShiny))
                .at(Vector3f(9f, 0f, 9f))
                .build()
        ).build()
        root.addChild(
            cube
        )
        torus = MeshRenderer(torusMesh, texture, blueShiny)
        sphere2 = MeshRenderer(
            mesh = TMeshResource(TMeshManager.getMesh("stall"), listOf(0, 1, 2)),
            texture = earthTex,
            material = blueShiny
        )
        sphere = SphereBillboardRenderer(
            texture = earthTex,
            material = redShiny,
            radius = 1.0f,
            position = Vector3f(-1f, 0f, 0f)
        )
        dragon = GameObject.withComponent(
            MeshRenderer(
                mesh = TMeshResource(TMeshManager.getMesh("dragon"), listOf(0, 1, 2)),
                material = yellowShiny
            ).apply { transform = Matrix4f().translate(Vector3f(10f, 0f, 0f)) }
        ).at(Vector3f(0f, 0f, 0f)).build()
        root.addGameComponent(torus)
        root.addChild(GameObject.withComponent(sphere).build())
        root.addChild(GameObject.withComponent(sphere2).at(Vector3f(1f, -1f, 0f)).build())
        root.addChild(dragon)
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