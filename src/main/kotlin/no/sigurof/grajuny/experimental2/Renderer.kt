package no.sigurof.grajuny.experimental2

import no.sigurof.grajuny.engine.DisplayManager
import no.sigurof.grajuny.entity.Camera
import no.sigurof.grajuny.shaders.ShaderManager
import no.sigurof.grajuny.shaders.settings.impl.StandardShader
import no.sigurof.grajuny.utils.Maths
import org.joml.Vector4f
import org.lwjgl.opengl.GL30

object Renderer {

    private val shader = StandardShader

    fun render(scene: SceneData, window: Long) {
//        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED)
        val camera = initCamera(scene, window)
        prepare(scene.backgroundColor)
        while (DisplayManager.isOpen()) {
            DisplayManager.eachFrameDo {
                play(scene, camera)
            }
        }

    }

    private fun initCamera(scene: SceneData, window: Long) : Camera{
        val camera = Camera.Builder(
            scene.mainCamera.eye,
            scene.mainCamera.target,
            20f
        ).build()
        camera.setCursorPosCallback(window)
        return camera
    }

    private fun play(scene: SceneData, camera: Camera) {
        ShaderManager.useShader(shader.program)
        shader.loadLightData(scene.lights[0])
        shader.loadProjectionMatrix(
            Maths.createProjectionMatrix(scene.mainCamera.fovy, scene.mainCamera.znear, scene.mainCamera.zfar)
        )
        shader.loadViewMatrix(Maths.createViewMatrix(camera))
        shader.loadCameraPosition(camera.pos)
    }

    private fun prepare(background: Vector4f) {
        GL30.glEnable(GL30.GL_DEPTH_TEST)
        GL30.glEnable(GL30.GL_CULL_FACE)
        GL30.glCullFace(GL30.GL_BACK)
        GL30.glClearColor(background.x, background.y, background.z, background.w)
        GL30.glClear(GL30.GL_COLOR_BUFFER_BIT or GL30.GL_DEPTH_BUFFER_BIT)
    }

}