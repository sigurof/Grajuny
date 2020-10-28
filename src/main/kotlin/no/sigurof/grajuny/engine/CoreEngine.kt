package no.sigurof.grajuny.engine

import no.sigurof.grajuny.display.DisplayManager
import no.sigurof.grajuny.game.Game
import no.sigurof.grajuny.postprocessing.PostProcessingManager
import no.sigurof.grajuny.resource.mesh.MeshManager
import no.sigurof.grajuny.resource.texture.TextureManager
import no.sigurof.grajuny.shader.ShaderManager

object CoreEngine {

    fun play(initGame: (window: Long) -> Game) {

        DisplayManager.FPS = 60
        DisplayManager.withWindowOpen { window ->
            val game = initGame(window)
            DisplayManager.eachFrameDo {
                PostProcessingManager.activeEffect?.activate()
                RenderingEngine.render(game)
                game.update()
                PostProcessingManager.activeEffect?.render()
            }
            TextureManager.cleanUp()
            MeshManager.cleanUp()
            ShaderManager.cleanUp()
        }

    }

}