package no.sigurof.grajuny

import no.sigurof.grajuny.context.DefaultSceneContext
import no.sigurof.grajuny.engine.DisplayManager
import no.sigurof.grajuny.engine.Visualization
import no.sigurof.grajuny.entity.Camera
import no.sigurof.grajuny.entity.Light
import no.sigurof.grajuny.entity.obj.PlainObject
import no.sigurof.grajuny.entity.obj.SphereBillboardObject
import no.sigurof.grajuny.entity.obj.TexturedBbdSphereObject
import no.sigurof.grajuny.entity.surface.DiffuseSpecularSurface
import no.sigurof.grajuny.renderer.CommonRenderer
import no.sigurof.grajuny.resource.ResourceManager
import no.sigurof.grajuny.scenario.Scenario
import no.sigurof.grajuny.shaders.settings.impl.BillboardShaderSettings
import no.sigurof.grajuny.shaders.settings.impl.StandardShader
import org.joml.Vector2f
import org.joml.Vector3f
import org.joml.Vector4f

fun main() {
    DisplayManager.withWindowOpen { window ->
        billboard(window)
    }
}

fun billboard(window: Long) {

    val origin = Vector3f(0f, 0f, 0f)
    val x = Vector3f(1f, 0f, 0f)

    val light = Light.Builder()
        .position(origin.add(Vector3f(0f, 5f, 0f), Vector3f()))
        .ambient(0.5f)
        .build()
    val camera = Camera.Builder()
        .at(Vector3f(16f + 6f, 2f, 16 + 6f))
        .lookingAt(origin)
        .withSpeed(12f)
        .build()

    val blue = Vector3f(0.1f, 0.4f, 0.9f)
    val red = Vector3f(0.9f, 0.1f, 0.1f)
    val white = Vector3f(1f, 1f, 1f)
    val reflectivity = 1f
    val damper = 100f

    val blueSurface = DiffuseSpecularSurface(damper, reflectivity, blue)

    val texDragon = CommonRenderer(
        StandardShader,
        ResourceManager.getTexturedMeshResource("dragon", "stall"),
        mutableListOf(
            PlainObject(
                blueSurface,
                origin,
                Vector3f(0f, -0.5f, 0f),
                Vector3f(1f, 1f, 1f)
            )
        )
    )

    val colDragon = CommonRenderer(
        StandardShader,
        ResourceManager.getMeshResource("cube"),
        mutableListOf(
            PlainObject(
                DiffuseSpecularSurface(damper, reflectivity, red),
                origin + 10 * x,
                Vector3f(0f, -0.5f, 0f),
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
                origin + 20 * x,
                Vector2f(0f, 0f),
                2f
            )
        )
    )

    val models = mutableListOf(colDragon, texDragon, texSoftBall)
    val context = DefaultSceneContext(
        camera = camera,
        light = light
    )
    val background = Vector4f(0.2f, 0.1f, 0.04f, 1f)

    DisplayManager.FPS = 60
    val scenario = Scenario(window, models, context, background)
    Visualization.play(scenario)
}

private operator fun Int.times(x: Vector3f): Vector3f {
    return x.mul(this.toFloat(), Vector3f())
}

private operator fun Vector3f.plus(x: Vector3f): Vector3f {
    return this.add(x, Vector3f())
}
