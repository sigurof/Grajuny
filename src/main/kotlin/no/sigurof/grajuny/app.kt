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
import no.sigurof.grajuny.shaders.settings.impl.PlainShaderSettings
import no.sigurof.grajuny.shaders.settings.impl.TextureShaderSettings
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
        .position(earthPos.add(Vector3f(0f, 50f, 0f), Vector3f()))
        .ambient(0.15f)
        .build()
    val camera = Camera.Builder()
        .at(Vector3f(16f + 6f, 2f, 16 + 6f))
        .lookingAt(earthPos)
        .withSpeed(12f)
        .build()

    val white = Vector3f(1f, 1f, 1f)
    val reflectivity = 1f
    val damper = 100f

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

    val dragon = CommonRenderer(
        PlainShaderSettings,
        ResourceManager.getMeshResource("cube"),
        mutableListOf(
            PlainObject(
                DiffuseSpecularSurface(damper, reflectivity, white),
                earthPos,
                Vector3f(0f, 0f, 0f),
                Vector3f(1f, 1f, 1f)
            )
        )
    )

    val texDragon = CommonRenderer(
        TextureShaderSettings,
        ResourceManager.getTexturedMeshResource("dragon", "stall"),
        mutableListOf(
            PlainObject(
                DiffuseSpecularSurface(damper, reflectivity, white),
                earthPos,
                Vector3f(0f, 0f, 0f),
                Vector3f(1f, 1f, 1f)
            )
        )
    )

    val objects = mutableListOf(
        SphereBillboardObject(
            DiffuseSpecularSurface(damper, reflectivity, white),
            Vector3f(10f, 1f, 1f),
            1f
        )
    )
    val colSoftBall = CommonRenderer(
        BillboardShaderSettings,
        ResourceManager.getBillboardResource(camera),
        objects
    )

    val models = mutableListOf(texDragon)
    val context = DefaultSceneContext(
        camera = camera,
        light = light
    )
    val background = Vector4f(0f, 0.5f, 0.1f, 1f)

    DisplayManager.FPS = 60
    val scenario = Scenario(window, models, context, background)
    Visualization.play(scenario)
}

