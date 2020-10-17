package no.sigurof.grajuny

import no.sigurof.grajuny.engine.CoreEngine
import no.sigurof.grajuny.game.SolarSystemGame

fun main() {
    CoreEngine.play { window ->
//        TestGame(window)
        SolarSystemGame(window)
    }
}
