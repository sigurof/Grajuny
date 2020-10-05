package no.sigurof.grajuny.restructuring

import no.sigurof.grajuny.resource.TextureManager

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