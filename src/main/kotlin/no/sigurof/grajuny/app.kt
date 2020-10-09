package no.sigurof.grajuny

import no.sigurof.grajuny.engine.TCoreEngine
import no.sigurof.grajuny.game.TTestGame

fun main() {
    TCoreEngine.play { window ->
        TTestGame(window)
    }
}
