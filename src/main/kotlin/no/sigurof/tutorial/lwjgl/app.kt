package no.sigurof.tutorial.lwjgl

import no.sigurof.tutorial.lwjgl.context.DefaultSceneContext
import no.sigurof.tutorial.lwjgl.engine.DisplayManager
import no.sigurof.tutorial.lwjgl.engine.Visualization
import no.sigurof.tutorial.lwjgl.entity.Camera
import no.sigurof.tutorial.lwjgl.entity.Light
import no.sigurof.tutorial.lwjgl.entity.obj.PlainObject
import no.sigurof.tutorial.lwjgl.entity.obj.SphereBillboardObject
import no.sigurof.tutorial.lwjgl.entity.obj.TexturedBbdSphereObject
import no.sigurof.tutorial.lwjgl.entity.surface.DiffuseSpecularSurface
import no.sigurof.tutorial.lwjgl.renderer.CommonRenderer
import no.sigurof.tutorial.lwjgl.resource.ResourceManager
import no.sigurof.tutorial.lwjgl.scenario.Scenario
import no.sigurof.tutorial.lwjgl.scenario.piHalf
import no.sigurof.tutorial.lwjgl.shaders.settings.impl.BillboardShaderSettings
import no.sigurof.tutorial.lwjgl.shaders.settings.impl.PlainShaderSettings
import no.sigurof.tutorial.lwjgl.shaders.settings.impl.TextureShaderSettings
import org.joml.Vector2f
import org.joml.Vector3f
import org.joml.Vector4f

fun main() {
    DisplayManager.withWindowOpen { window ->
        billboard(window)
    }
}

fun billboard(window: Long) {

    val earthPos = Vector3f(0f, 0f, 0f);

    val light = Light.Builder()
        .position(earthPos.add(Vector3f(0f, 5f, 0f), Vector3f()))
        .ambient(0.5f)
        .build()
    val camera = Camera.Builder()
        .at(Vector3f(16f + 6f, 2f, 16 + 6f))
        .lookingAt(earthPos)
        .withSpeed(12f)
        .build()

    val blue = Vector3f(0.1f, 0.4f, 0.9f)
    val red = Vector3f(0.9f, 0.1f, 0.1f)
    val white = Vector3f(1f, 1f, 1f)
    val reflectivity = 1f
    val damper = 100f

    val blueSurface = DiffuseSpecularSurface(damper, reflectivity, blue)

    val texDragon = CommonRenderer(
        TextureShaderSettings,
        ResourceManager.getTexturedMeshResource("dragon", "stall"),
        mutableListOf(
            PlainObject(
                blueSurface,
                Vector3f(-6f, 0f, 0f),
                Vector3f(0f, -piHalf, 0f),
                Vector3f(1f, 1f, 1f)
            )
        )
    )

    val colDragon = CommonRenderer(
        PlainShaderSettings,
        ResourceManager.getMeshResource("dragon"),
        mutableListOf(
            PlainObject(
                DiffuseSpecularSurface(damper, reflectivity, red),
                Vector3f(0f, 0f, 0f),
                Vector3f(0f, -piHalf, 0f),
                Vector3f(1f, 1f, 1f)
            )
        )
    )

    val coloredBalls = mutableListOf<SphereBillboardObject>()
    val f = 5f
    val lim = 1
    for (i in -lim..lim) {
        for (j in -lim..lim) {
            for (k in -lim..lim) {
                val pos = Vector3f(i.toFloat(), j.toFloat(), k.toFloat()).mul(f)
                coloredBalls.add(
                    SphereBillboardObject(
                        blueSurface,
                        pos,
                        1f
                    )
                )
            }
        }
    }

    val texSoftBall = CommonRenderer(
        BillboardShaderSettings,
        ResourceManager.getTexturedBillboardResource("earth8192"),
        mutableListOf(
            TexturedBbdSphereObject(
                DiffuseSpecularSurface(damper, reflectivity, white),
                earthPos,
                Vector2f(0f, 0f),
                15f
            )
        )
    )

    val models = mutableListOf(texSoftBall)
    val context = DefaultSceneContext(
        camera = camera,
        light = light
    )
    val background = Vector4f(0f, 0f, 0f, 1f);

    DisplayManager.FPS = 60
    val scenario = Scenario(window, models, context, background)
    Visualization.play(scenario)
}

