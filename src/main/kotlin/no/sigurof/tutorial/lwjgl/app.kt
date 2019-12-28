package no.sigurof.tutorial.lwjgl

import no.sigurof.tutorial.lwjgl.engine.DisplayManager
import no.sigurof.tutorial.lwjgl.engine.Visualization
import no.sigurof.tutorial.lwjgl.scenario.SandboxScenario


fun main() {
    DisplayManager.FPS = 60
    DisplayManager.withWindowOpen { window: Long ->
        val scenario = SandboxScenario.default(window)
        Visualization.play(
            scenario.shader,
            scenario.camera,
            scenario.light,
            scenario.entities
        )
    }
}

fun billboard() {
/*
    DisplayManager.FPS = 3
    DisplayManager.createDisplay()
    val vao = glGenVertexArrays()
    glBindVertexArray(vao)
    val shader = BillboardShader()
    val billboard = SphereBillboard()
    val camera = Camera(window = DisplayManager.window ?: error("Need to have a window!"))
    val renderer = Renderer()
    while (DisplayManager.isOpen()) {
        DisplayManager.eachFrameDo {
            //            glEnable(GL_DEPTH_TEST)
            glClearColor(0.2f, 0.3f, 0.1f, 1.0f)
            glClear(GL_COLOR_BUFFER_BIT)
            println(currentTimeMillis())
            shader.loadViewMatrix(camera)
//            shader.load
            billboard.render(shader)
        }
    }
    glBindVertexArray(0)

    DisplayManager.closeDisplay()
*/
}

