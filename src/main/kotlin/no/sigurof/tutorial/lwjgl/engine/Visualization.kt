package no.sigurof.tutorial.lwjgl.engine

import no.sigurof.tutorial.lwjgl.scenario.Scenario

class Visualization {

    companion object {

        fun play(scenario: Scenario) {
            scenario.prepare()
            while (DisplayManager.isOpen()) {
                DisplayManager.eachFrameDo {
                    scenario.run()
                }
            }
            scenario.cleanUp()
        }

    }
}