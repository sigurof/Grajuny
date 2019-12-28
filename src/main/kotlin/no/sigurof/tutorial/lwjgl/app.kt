package no.sigurof.tutorial.lwjgl

import no.sigurof.tutorial.lwjgl.engine.DisplayManager
import no.sigurof.tutorial.lwjgl.engine.Visualization
import no.sigurof.tutorial.lwjgl.scenario.BillboardScenario
import no.sigurof.tutorial.lwjgl.scenario.SandboxScenario


fun main() {
    DisplayManager.withWindowOpen { window ->
        billboard(window)
//        sandbox(window)
    }
}

fun sandbox(window: Long) {
    DisplayManager.FPS = 60
    val scenario = SandboxScenario.default(window)
    Visualization.play(scenario)
}

fun billboard(window: Long) {
    DisplayManager.FPS = 60
    val scenario = BillboardScenario.default(window)
    Visualization.play(scenario)
}

