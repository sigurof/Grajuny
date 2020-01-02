package no.sigurof.tutorial.lwjgl

import no.sigurof.tutorial.lwjgl.engine.DisplayManager
import no.sigurof.tutorial.lwjgl.engine.Visualization
import no.sigurof.tutorial.lwjgl.scenario.Scenario

fun main() {
    DisplayManager.withWindowOpen { window ->
        billboard(window)
//        sandbox(window)
    }
}

fun billboard(window: Long) {
    DisplayManager.FPS = 60
    val scenario = Scenario.default(window)
    Visualization.play(scenario)
}

