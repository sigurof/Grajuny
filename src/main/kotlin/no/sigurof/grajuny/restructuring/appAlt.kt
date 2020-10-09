package no.sigurof.grajuny.restructuring

import no.sigurof.grajuny.restructuring.engine.TCoreEngine


fun main() {
    TCoreEngine.play { window ->
        TTestGame(window)
    }
}
