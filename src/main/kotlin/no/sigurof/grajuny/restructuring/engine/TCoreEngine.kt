package no.sigurof.grajuny.restructuring.engine

import no.sigurof.grajuny.resource.TextureManager
import no.sigurof.grajuny.restructuring.TDisplayManager
import no.sigurof.grajuny.restructuring.TGame
import no.sigurof.grajuny.restructuring.TMeshManager
import no.sigurof.grajuny.restructuring.TShaderManager

object TCoreEngine {

    fun play(initGame: (window: Long) -> TGame) {

        TDisplayManager.FPS = 60
        TDisplayManager.withWindowOpen { window ->
            val renderingEngine = RenderingEngine(window)
            val game = initGame(window)
            while (TDisplayManager.isOpen()) {
                TDisplayManager.eachFrameDo {
                    renderingEngine.render(game)
                }
            }
            TextureManager.cleanUp()
            TMeshManager.cleanUp()
            TShaderManager.cleanUp()
        }

    }

}