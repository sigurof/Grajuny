package no.sigurof.grajuny

import no.sigurof.grajuny.engine.CoreEngine
import no.sigurof.grajuny.game.TestGame

fun main() {
    CoreEngine.play { window ->
        TestGame(window)
    }
}
