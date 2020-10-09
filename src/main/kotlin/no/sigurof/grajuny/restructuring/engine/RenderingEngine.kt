package no.sigurof.grajuny.restructuring.engine

import no.sigurof.grajuny.restructuring.TCamera
import no.sigurof.grajuny.restructuring.TGame
import no.sigurof.grajuny.restructuring.shader.Shader
import org.joml.Vector4f
import org.lwjgl.opengl.GL11.GL_BACK
import org.lwjgl.opengl.GL11.GL_CULL_FACE
import org.lwjgl.opengl.GL11.GL_DEPTH_TEST
import org.lwjgl.opengl.GL11.glClearColor
import org.lwjgl.opengl.GL11.glCullFace
import org.lwjgl.opengl.GL11.glEnable
import org.lwjgl.opengl.GL30

class RenderingEngine(
    val window: Long
) {

    init {
        glClearColor(0f, 0f, 0f, 0f)
        glCullFace(GL_BACK)
        glEnable(GL_CULL_FACE)
        glEnable(GL_DEPTH_TEST)
    }

    fun render(game: TGame) {
        clearScreen(game.background)
        shaders.forEach { shader ->
            shader.use()
            game.doNecessaryStuff(shader)
            game.onUpdate()
            game.render(shader)
        }
        TCamera.activeCamera?.move(window)
    }

    private fun clearScreen(background: Vector4f) {
        GL30.glClear(GL30.GL_COLOR_BUFFER_BIT or GL30.GL_DEPTH_BUFFER_BIT)
        GL30.glClearColor(background.x, background.y, background.z, background.w)
    }

    companion object{
        val shaders = mutableSetOf<Shader>()
    }
}
