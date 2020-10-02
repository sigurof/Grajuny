package no.sigurof.grajuny

import no.sigurof.grajuny.context.DefaultSceneContext
import no.sigurof.grajuny.engine.DisplayManager
import no.sigurof.grajuny.entity.Camera
import no.sigurof.grajuny.entity.Light
import no.sigurof.grajuny.entity.obj.SphereBillboardObject
import no.sigurof.grajuny.entity.obj.TexturedBbdSphereObject
import no.sigurof.grajuny.entity.surface.DiffuseSpecularSurface
import no.sigurof.grajuny.renderer.CommonRenderer
import no.sigurof.grajuny.resource.ResourceManager
import no.sigurof.grajuny.scenario.Scenario
import no.sigurof.grajuny.shaders.settings.impl.BillboardShaderSettings
import no.sigurof.grajuny.utils.WHITE
import no.sigurof.grajuny.utils.YELLOW
import org.joml.Vector2f
import org.joml.Vector3f
import org.joml.Vector4f

// ide: kan man ikke bare encapsulate alt,
// dvs. overflate, tekstur, mesh, osv på et objekt.
// Når rendereren går gjennom hvert objekt sjekker den om denne ressursen allerede er aktivert eller ikke.

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

    val reflectivity = 1f
    val damper = 100f

    val coloredBall = SphereBillboardObject(
        DiffuseSpecularSurface(damper, reflectivity, YELLOW),
        Vector3f(10f, 0f, 0f),
        5f
    )
    val coloredSoftBall = CommonRenderer(
        shader = BillboardShaderSettings,
        resource = ResourceManager.getBillboardResource(),
        objects = mutableListOf(
            coloredBall
        )
    )

    val texSoftBall = CommonRenderer(
        shader = BillboardShaderSettings,
        resource = ResourceManager.getTexturedBillboardResource("earth8192"),
        objects = mutableListOf(
            TexturedBbdSphereObject(
                DiffuseSpecularSurface(damper, reflectivity, WHITE),
                earthPos,
                Vector2f(0f, 0f),
                5f
            )
        )
    )

    val models = mutableListOf(texSoftBall, coloredSoftBall)
    val context = DefaultSceneContext(
        camera = camera,
        light = light
    )
    val background = Vector4f(0f, 0f, 0f, 1f);

    DisplayManager.FPS = 60
    val scenario = Scenario(
        window = window,
        renderers = models,
        context = context,
        background = background
    )
    scenario.prepare()
    while (DisplayManager.isOpen()) {
        DisplayManager.eachFrameDo {
            scenario.run()
        }
    }
    scenario.cleanUp()
}

