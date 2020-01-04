package no.sigurof.tutorial.lwjgl

import no.sigurof.tutorial.lwjgl.context.DefaultSceneContext
import no.sigurof.tutorial.lwjgl.engine.DisplayManager
import no.sigurof.tutorial.lwjgl.engine.Visualization
import no.sigurof.tutorial.lwjgl.entity.Camera
import no.sigurof.tutorial.lwjgl.entity.Light
import no.sigurof.tutorial.lwjgl.entity.obj.PlainObject
import no.sigurof.tutorial.lwjgl.entity.obj.SphereBillboardObject
import no.sigurof.tutorial.lwjgl.entity.surface.DiffuseSpecularSurface
import no.sigurof.tutorial.lwjgl.renderer.BillboardRenderer
import no.sigurof.tutorial.lwjgl.renderer.PlainRenderer
import no.sigurof.tutorial.lwjgl.renderer.TexturedBillboardRenderer
import no.sigurof.tutorial.lwjgl.renderer.TexturedRenderer
import no.sigurof.tutorial.lwjgl.resource.TextureManager
import no.sigurof.tutorial.lwjgl.scenario.Scenario
import no.sigurof.tutorial.lwjgl.scenario.piHalf
import no.sigurof.tutorial.lwjgl.utils.ORIGIN
import org.joml.Vector3f

fun main() {
    DisplayManager.withWindowOpen { window ->
        billboard(window)
    }
}

fun billboard(window: Long) {

    val light = Light.Builder()
        .position(Vector3f(0f, 11f, 0f))
        .ambient(0f)
        .build()
    val camera = Camera.Builder()
        .at(Vector3f(0f, 15f, -15f))
        .lookingAt(ORIGIN)
        .withSpeed(4f)
        .build()

    val blue = Vector3f(0.1f, 0.4f, 0.9f)
    val red = Vector3f(0.9f, 0.1f, 0.1f)
    val reflectivity = 1f
    val damper = 100f

    val blueSurface = DiffuseSpecularSurface(damper, reflectivity, blue)
    val texDragon = TexturedRenderer.Builder()
        .withModel("dragon")
        .withTexture("stall")
        .withObjects(
            mutableListOf(
                PlainObject(
                    blueSurface,
                    Vector3f(-6f, 0f, 0f),
                    Vector3f(0f, -piHalf, 0f),
                    Vector3f(1f, 1f, 1f)
                )
            )
        )
        .build()

    val colDragon = PlainRenderer.Builder()
        .withModel("dragon")
        .withObjects(
            mutableListOf(
                PlainObject(
                    DiffuseSpecularSurface(damper, reflectivity, red),
                    Vector3f(0f, 0f, 0f),
                    Vector3f(0f, -piHalf, 0f),
                    Vector3f(1f, 1f, 1f)
                )
            )
        )
        .build()

    val coloredBalls = mutableListOf<SphereBillboardObject>()
    val f = 5f
    val lim = 5
    for (i in -lim..lim) {
        for (j in -lim..lim) {
            for (k in -lim..lim) {
                val pos = Vector3f(i.toFloat(), j.toFloat(), k.toFloat()).mul(f)
                coloredBalls.add(
                    SphereBillboardObject(
                        blueSurface, pos, 0.5f
                    )
                )
            }
        }
    }

    val colSoftBall = BillboardRenderer(camera).addObjects(
        coloredBalls
    )

    val texSoftBall = TexturedBillboardRenderer(
        camera,
        TextureManager.get("default"),
        mutableListOf(
            SphereBillboardObject(
                DiffuseSpecularSurface(damper, reflectivity, red),
                Vector3f(6f, 0f, 0f),
                1f
            )
        )
    )
    val models = mutableListOf(colSoftBall)
    val context = DefaultSceneContext(
        camera = camera,
        light = light
    )

    DisplayManager.FPS = 60
    val scenario = Scenario(window, models, context)
    Visualization.play(scenario)
}

