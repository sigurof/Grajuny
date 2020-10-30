package no.sigurof.grajuny.game

import no.sigurof.grajuny.camera.impl.FpsCamera
import no.sigurof.grajuny.color.RED
import no.sigurof.grajuny.color.WHITE
import no.sigurof.grajuny.color.YELLOW
import no.sigurof.grajuny.components.MeshRenderer
import no.sigurof.grajuny.components.SphereBillboardRenderer
import no.sigurof.grajuny.components.TraceRenderer
import no.sigurof.grajuny.node.GameComponent
import no.sigurof.grajuny.node.GameObject
import no.sigurof.grajuny.resource.material.RegularMaterial
import no.sigurof.grajuny.resource.texture.TextureRenderer
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector3fc
import org.joml.Vector4f
import kotlin.math.cos
import kotlin.math.sin

class TestGame(
    window: Long
) : Game(
    window = window,
    background = Vector4f(0f, 0.0f, 0f, 1f)
) {
    private var sphereObj: GameObject
    private val cube: GameObject
    private val camera: FpsCamera

    private val sphere: GameComponent

    init {
        val earthTex = TextureRenderer.fromName("earth512")
        val redShiny = RegularMaterial(RED, 1f, 100f)
        val yellowShiny = RegularMaterial(YELLOW, 10f, 100f)
        cube = GameObject.withChild(
            GameObject.withComponent(
                MeshRenderer(
                    meshName = "cube",
                    textureRenderer = TextureRenderer.fromName("earth512"),
                    material = yellowShiny
                )
            )
                .at(Vector3f(-3f, 0f, 0f))
                .build()
        ).build()
        root.addChild(
            cube
        )
        sphere = SphereBillboardRenderer(
            textureRenderer = earthTex,
            material = redShiny,
            radius = 1f,
            position = Vector3f(0f, 0f, 0f)
        )
        sphereObj = GameObject.withComponent(sphere).build()
        root.addChild(sphereObj)
        val cameraPos = Vector3f(0f, 0f, 20f)
        camera = FpsCamera.Builder()
            .at(cameraPos)
            .lookingAt(cube.getPosition())
            .capturingMouseInput(window)
            .build()
        TraceRenderer.Builder(color = WHITE, numberOfPoints = 50)
            .attachTo(sphereObj)
            .build()
    }

    override fun onUpdate() {
        val elapsedSeconds = elapsedMs / 1000f
//        background = Vector4f(sin(elapsedSeconds).absoluteValue, 0.5f, 0.5f, 1f)
        val angle = deltaTime / 1000f
//        torus.transform.rotate(angle, Vector3f(0f, 1f, 0f))
        cube.rotate(angle, Vector3f(0f, 0f, 1f))
        sphereObj.transform =
            Matrix4f().translate(10 * Vector3f(cos(elapsedSeconds), sin(elapsedSeconds), sin(sin(elapsedSeconds))))
    }

}

private operator fun Number.times(vector3f: Vector3f): Vector3fc? {
    return vector3f.mul(this.toFloat())
}
