package no.sigurof.grajuny.engine

import no.sigurof.grajuny.game.Game
import no.sigurof.grajuny.shader.ShaderManager
import org.joml.Vector4f
import org.lwjgl.opengl.GL11.GL_BACK
import org.lwjgl.opengl.GL11.GL_CULL_FACE
import org.lwjgl.opengl.GL11.GL_DEPTH_TEST
import org.lwjgl.opengl.GL11.glClearColor
import org.lwjgl.opengl.GL11.glCullFace
import org.lwjgl.opengl.GL11.glEnable
import org.lwjgl.opengl.GL30

object RenderingEngine {

    init {
        glClearColor(0f, 0f, 0f, 0f)
        glCullFace(GL_BACK)
        glEnable(GL_CULL_FACE)
        glEnable(GL_DEPTH_TEST)
    }

    fun render(game: Game) {
        clearScreen(game.background)
        ShaderManager.activeShaders.forEach { shader ->
            shader.use()
            game.render(shader)
        }
    }

    private fun clearScreen(background: Vector4f) {
        GL30.glClear(GL30.GL_COLOR_BUFFER_BIT or GL30.GL_DEPTH_BUFFER_BIT)
        GL30.glClearColor(background.x, background.y, background.z, background.w)
    }
}
