package no.sigurof.grajuny.game

import no.sigurof.grajuny.camera.impl.FpsCamera
import no.sigurof.grajuny.color.RED
import no.sigurof.grajuny.color.WHITE
import no.sigurof.grajuny.color.YELLOW
import no.sigurof.grajuny.components.MeshRenderer
import no.sigurof.grajuny.components.SphereBillboardRenderer
import no.sigurof.grajuny.components.TraceRenderer
import no.sigurof.grajuny.light.LightSource
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
//        torus = MeshRenderer(torusMesh, texture, blueShiny)
//        sphere2 = MeshRenderer(
//            mesh = MeshResource(MeshManager.getMesh("sphere"), listOf(0, 1, 2)),
//            texture = earthTex,
//            material = blueShiny
//        )
        sphere = SphereBillboardRenderer(
            textureRenderer = earthTex,
            material = redShiny,
            radius = 1f,
            position = Vector3f(0f, 0f, 0f)
        )
        sphereObj = GameObject.withComponent(sphere).build()
        root.addChild(sphereObj)
//        dragon = GameObject.withComponent(
//            MeshRenderer(
//                mesh = MeshResource(MeshManager.getMesh("dragon"), listOf(0, 1, 2)),
//                material = yellowShiny,
//                shadersToUse = listOf(SilhouetteShader)
//            ).apply { transform = Matrix4f().translate(Vector3f(10f, 0f, 0f)) }
//        ).at(Vector3f(0f, 0f, 0f)).build()
//        root.addGameComponent(torus)
//        root.addChild(GameObject.withComponent(sphere).build())
//        root.addChild(GameObject.withComponent(sphere2).at(Vector3f(1f, -1f, 0f)).build())
//        root.addChild(dragon)
        // Make light
        LightSource.Builder().position(Vector3f(0f, 100f, 40f)).build()
        val cameraPos = Vector3f(0f, 0f, 20f)
        camera = FpsCamera.Builder()
            .at(cameraPos)
            .lookingAt(cube.getPosition())
            .capturingMouseInput(window)
            .build()
/*
        line = LineRenderer(
            line = LineResource.fromPoints(
                mutableListOf(
                    ORIGIN, Vector3f(1f, 1f, 0f), Vector3f(2f, 2f, 0.1f)
                )
            ),
            color = RED
        )
*/
//        root.addGameComponent(line)
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
