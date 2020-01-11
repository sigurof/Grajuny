package no.sigurof.grajuny.engine

import no.sigurof.grajuny.scenario.Scenario
/**
 * A `Visualization` is an animation
 * */
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