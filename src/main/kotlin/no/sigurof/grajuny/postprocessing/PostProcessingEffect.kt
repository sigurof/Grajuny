package no.sigurof.grajuny.postprocessing

import no.sigurof.grajuny.display.DisplayManager
import no.sigurof.grajuny.resource.QuadResource
import no.sigurof.grajuny.resource.texture.TextureManager
import no.sigurof.grajuny.resource.texture.TextureRenderer
import no.sigurof.grajuny.shader.ShaderManager
import no.sigurof.grajuny.shader.shaders.PostProcessingShader
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT
import org.lwjgl.opengl.GL11.GL_DEPTH_TEST
import org.lwjgl.opengl.GL11.GL_RGB
import org.lwjgl.opengl.GL11.GL_TEXTURE_2D
import org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE
import org.lwjgl.opengl.GL11.glBindTexture
import org.lwjgl.opengl.GL11.glClear
import org.lwjgl.opengl.GL11.glDisable
import org.lwjgl.opengl.GL11.glEnable
import org.lwjgl.opengl.GL11.glTexImage2D
import org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0
import org.lwjgl.opengl.GL30.GL_COLOR_BUFFER_BIT
import org.lwjgl.opengl.GL30.GL_DEPTH24_STENCIL8
import org.lwjgl.opengl.GL30.GL_DEPTH_STENCIL_ATTACHMENT
import org.lwjgl.opengl.GL30.GL_FRAMEBUFFER
import org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE
import org.lwjgl.opengl.GL30.GL_RENDERBUFFER
import org.lwjgl.opengl.GL30.glBindFramebuffer
import org.lwjgl.opengl.GL30.glBindRenderbuffer
import org.lwjgl.opengl.GL30.glCheckFramebufferStatus
import org.lwjgl.opengl.GL30.glFramebufferRenderbuffer
import org.lwjgl.opengl.GL30.glFramebufferTexture2D
import org.lwjgl.opengl.GL30.glGenFramebuffers
import org.lwjgl.opengl.GL30.glGenRenderbuffers
import org.lwjgl.opengl.GL30.glRenderbufferStorage
import java.nio.FloatBuffer

// TODO should initialize on constructor?
class PostProcessingEffect {
    private lateinit var textureRenderer: TextureRenderer
    private lateinit var quad: QuadResource
    private var framebuffer: Int = 0
    private var rbo: Int = 0

    fun initialize() {
        val width = DisplayManager.WIDTH
        val height = DisplayManager.HEIGHT

        framebuffer = glGenFramebuffers()
        glBindFramebuffer(GL_FRAMEBUFFER, framebuffer)

        textureRenderer = TextureManager.getEmptyTexture(
            width,
            height
        )
        glFramebufferTexture2D(
            GL_FRAMEBUFFER,
            GL_COLOR_ATTACHMENT0,
            GL_TEXTURE_2D,
            textureRenderer.textureResource.tex,
            0
        )

        rbo = glGenRenderbuffers()
        glBindRenderbuffer(GL_RENDERBUFFER, rbo)
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH24_STENCIL8, width, height)
        glBindRenderbuffer(GL_RENDERBUFFER, 0)
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_RENDERBUFFER, rbo)

        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            throw IllegalStateException("Expected framebuffer to be complete. However, it is not complete.")
        }
        glBindFramebuffer(GL_FRAMEBUFFER, 0)
        ShaderManager.activePostProcessingShader = PostProcessingShader

        quad = QuadResource()

        GLFW.glfwSetWindowSizeCallback(DisplayManager.window!!, ::windowResizeCallback)

    }

    private fun windowResizeCallback(window: Long, width: Int, height: Int) {
        DisplayManager.resizeWindow(width, height)
        resizeTexture(width, height)
        resizeRbo(width, height)
    }

    fun activate() {
        glBindFramebuffer(GL_FRAMEBUFFER, framebuffer)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        glEnable(GL_DEPTH_TEST)
    }

    fun render() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0)
        glClear(GL_COLOR_BUFFER_BIT)

        ShaderManager.activePostProcessingShader!!.use()
        quad.activate()
        glDisable(GL_DEPTH_TEST)
        textureRenderer.activate()
        quad.render()
        quad.deactivate()
        textureRenderer.deactivate()
    }

    private fun resizeTexture(width: Int, height: Int) {
        val nul: FloatBuffer? = null
        glBindTexture(GL_TEXTURE_2D, textureRenderer.textureResource.tex)
        glTexImage2D(
            GL_TEXTURE_2D, 0, GL_RGB, width, height, 0,
            GL_RGB, GL_UNSIGNED_BYTE, nul
        )
        glBindTexture(GL_TEXTURE_2D, 0)
    }

    private fun resizeRbo(width: Int, height: Int) {
        glBindRenderbuffer(GL_RENDERBUFFER, rbo)
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH24_STENCIL8, width, height)
        glBindRenderbuffer(GL_RENDERBUFFER, 0)

    }

    fun destroy() {

    }

}