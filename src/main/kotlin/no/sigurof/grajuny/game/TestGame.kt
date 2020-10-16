package no.sigurof.grajuny.game

import no.sigurof.grajuny.camera.Camera
import no.sigurof.grajuny.components.MeshRenderer
import no.sigurof.grajuny.components.SphereBillboardRenderer
import no.sigurof.grajuny.components.TraceRenderer
import no.sigurof.grajuny.light.LightSource
import no.sigurof.grajuny.node.GameComponent
import no.sigurof.grajuny.node.GameObject
import no.sigurof.grajuny.resource.MeshResource
import no.sigurof.grajuny.resource.material.ReflectiveMaterial
import no.sigurof.grajuny.resource.mesh.MeshManager
import no.sigurof.grajuny.resource.texture.Texture
import no.sigurof.grajuny.resource.texture.TextureManager
import no.sigurof.grajuny.utils.RED
import no.sigurof.grajuny.utils.WHITE
import no.sigurof.grajuny.utils.YELLOW
import org.joml.Vector3f
import org.joml.Vector4f

class TestGame(
    window: Long
) : Game(
    window = window,
    background = Vector4f(0f, 0.0f, 0f, 1f)
) {
    private val cube: GameObject
    override val camera: Camera
//    private val torus: GameComponent
    private val sphere: GameComponent
//    private val sphere2: GameComponent
//    private val dragon: GameObject
//    private val line: GameComponent

    init {
//        val texture = Texture(TextureManager.get("stall"))
        val earthTex = Texture(TextureManager.get("earth512"))
//        val torusMesh = MeshResource(MeshManager.getMesh("torus"), listOf(0, 1, 2))
        val cubeMesh = MeshResource(MeshManager.getMesh("cube"), listOf(0, 1, 2))
//        val blueShiny = ReflectiveMaterial(BLUE, 1f, 100f)
        val redShiny = ReflectiveMaterial(RED, 1f, 100f)
        val yellowShiny = ReflectiveMaterial(YELLOW, 10f, 100f)
        cube = GameObject.withChild(
            GameObject.withComponent(
                MeshRenderer(
                    mesh = cubeMesh,
                    texture = null,
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
            texture = earthTex,
            material = redShiny,
            radius = 0.1f,
            position = Vector3f(0f, 0f, 0f)
        )
        root.addChild(GameObject.withComponent(sphere).build())
//        dragon = GameObject.withComponent(
//            MeshRenderer(
//                mesh = MeshResource(MeshManager.getMesh("dragon"), listOf(0, 1, 2)),
//                material = yellowShiny,
//                shadersToUse = listOf(FeaturelessShader)
//            ).apply { transform = Matrix4f().translate(Vector3f(10f, 0f, 0f)) }
//        ).at(Vector3f(0f, 0f, 0f)).build()
//        root.addGameComponent(torus)
//        root.addChild(GameObject.withComponent(sphere).build())
//        root.addChild(GameObject.withComponent(sphere2).at(Vector3f(1f, -1f, 0f)).build())
//        root.addChild(dragon)
        // Make light
        LightSource.Builder().position(Vector3f(0f, 100f, 40f)).build()
        val cameraPos = Vector3f(0f, 0f, 20f)
        camera = Camera.Builder()
            .at(cameraPos)
            .lookingAt(cube.getPosition())
//            .capturingMouseInput(window)
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
            .attachTo(cube.children.first())
            .build()
    }

    override fun onUpdate() {
        val elapsedSeconds = elapsedMs / 1000f
//        background = Vector4f(sin(elapsedSeconds).absoluteValue, 0.5f, 0.5f, 1f)
        val angle = deltaTime / 1000f
//        torus.transform.rotate(angle, Vector3f(0f, 1f, 0f))
        cube.rotate(angle, Vector3f(0f, 0f, 1f))
    }

}