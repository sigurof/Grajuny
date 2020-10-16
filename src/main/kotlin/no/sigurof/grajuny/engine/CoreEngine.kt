package no.sigurof.grajuny.engine

import no.sigurof.grajuny.display.DisplayManager
import no.sigurof.grajuny.game.Game
import no.sigurof.grajuny.resource.mesh.MeshManager
import no.sigurof.grajuny.resource.texture.TextureManager
import no.sigurof.grajuny.shader.ShaderManager

object CoreEngine {

    fun play(initGame: (window: Long) -> Game) {

        DisplayManager.FPS = 60
        DisplayManager.withWindowOpen { window ->
            val renderingEngine = RenderingEngine(window)
            val game = initGame(window)
            while (DisplayManager.isOpen()) {
                DisplayManager.eachFrameDo {
                    renderingEngine.render(game)
                }
            }
            TextureManager.cleanUp()
            MeshManager.cleanUp()
            ShaderManager.cleanUp()
        }

    }

}