package no.sigurof.grajuny.engine

import no.sigurof.grajuny.display.TDisplayManager
import no.sigurof.grajuny.game.TGame
import no.sigurof.grajuny.resource.TextureManager
import no.sigurof.grajuny.resource.mesh.TMeshManager
import no.sigurof.grajuny.shader.ShaderManager

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
            ShaderManager.cleanUp()
        }

    }

}