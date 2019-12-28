package no.sigurof.tutorial.lwjgl.scenario

import no.sigurof.tutorial.lwjgl.engine.DisplayManager
import no.sigurof.tutorial.lwjgl.entity.Camera
import no.sigurof.tutorial.lwjgl.entity.SphereBillboard
import no.sigurof.tutorial.lwjgl.shaders.BillboardShader
import org.joml.Matrix4f
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30.glBindVertexArray
import org.lwjgl.opengl.GL30.glGenVertexArrays

class BillboardScenario private constructor(
    private val camera: Camera,
    private val shader: BillboardShader,
    private val billboard: SphereBillboard,
    private val FOV: Float = 70f,
    private val NEAR_PLANE: Float = 0.1f,
    private val FAR_PLANE: Float = 1000f
) : Scenario {

    companion object {
        fun default(window: Long): BillboardScenario {
            val vao = glGenVertexArrays()
            glBindVertexArray(vao)
            val shader = BillboardShader()
            val billboard = SphereBillboard()
            val camera = Camera(window = window)
            return BillboardScenario(camera, shader, billboard)
        }

    }

    override fun prepare() {
        uploadProjectionMatrix(shader)
    }

    override fun run() {
        camera.move()
        prepareFrame()
        shader.start()
        shader.loadViewMatrix(camera)
        billboard.render(shader)
        shader.stop()
    }

    override fun cleanUp() {
        shader.cleanUp()
        glBindVertexArray(0)
    }

    private fun prepareFrame() {
        GL11.glClearColor(0.2f, 0.3f, 0.1f, 1.0f)
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT)
    }


    private fun uploadProjectionMatrix(shader: BillboardShader) {
        val projectionMatrix = createProjectionMatrix()
        shader.start()
        shader.loadProjectionMatrix(projectionMatrix)
        shader.stop()
    }


    private fun createProjectionMatrix(): Matrix4f {
        val aspectRatio = DisplayManager.WIDTH.toFloat() / DisplayManager.HEIGHT.toFloat()
        val yScale = ((1f / Math.tan(Math.toRadians(FOV / 2.0))) * aspectRatio).toFloat()
        val xScale = yScale / aspectRatio
        val frustumLength = FAR_PLANE - NEAR_PLANE

        val projectionMatrix = Matrix4f()
        projectionMatrix.m00(xScale)// = xScale
        projectionMatrix.m00(xScale)
        projectionMatrix.m11(yScale)
        projectionMatrix.m22(-(FAR_PLANE + NEAR_PLANE) / frustumLength)
        projectionMatrix.m23(-1f)
        projectionMatrix.m32(-(2 * NEAR_PLANE * FAR_PLANE) / frustumLength)
        projectionMatrix.m33(0f)
        return projectionMatrix
    }


}