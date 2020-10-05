package no.sigurof.grajuny.restructuring

fun main() {
    TCoreEngine.play { window ->
        TTestGame(window)
    }
}

/*
fun playGameOld() {
    DisplayManager.withWindowOpen { window ->
        val origin = Vector3f(0f, 0f, 0f)
        val sceneNode = GameObject()
        sceneNode.addChild()

        val camera = Camera.Builder()
            .at(Vector3f(16f + 6f, 2f, 16 + 6f))
            .lookingAt(origin)
            .withSpeed(12f)
            .build()
        DisplayManager.FPS = 60
        DisplayManager.withWindowOpen {
            prepare(window, camera)
            while (DisplayManager.isOpen()) {
                DisplayManager.eachFrameDo {
                    run(camera, window, Vector4f(1f, 1f, 0.5f, 1f))
                }
            }
            cleanUp()
        }
    }
}

private fun prepare(window: Long, camera: Camera) {
    GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED)
    camera.setCursorPosCallback(window)
}

private fun run(camera: Camera, window: Long, background: Vector4f) {
    camera.move(window)
    prepareFrame(background)
    for (model in renderers) {
        ShaderManager.useShader(shader.program)
        context.loadUniforms(shader)
        resource.activate()
        shader.loadUseTexture(false)
        for (obj in objects) {
            obj.loadUniforms(shader)
            resource.render()
        }
        resource.deactivate()
        ShaderManager.stop()
    }
}

fun cleanUp() {
    for (renderer in renderers) {
        renderer.cleanShader()
    }
}

private fun prepareFrame(background: Vector4f) {
    GL30.glEnable(GL30.GL_DEPTH_TEST)
    GL30.glEnable(GL30.GL_CULL_FACE)
    GL30.glCullFace(GL30.GL_BACK)
    GL30.glClearColor(background.x, background.y, background.z, background.w)
    GL30.glClear(GL30.GL_COLOR_BUFFER_BIT or GL30.GL_DEPTH_BUFFER_BIT)
}

fun play() {
}

fun billboard(window: Long) {

}

private operator fun Int.times(x: Vector3f): Vector3f {
    return x.mul(this.toFloat(), Vector3f())
}

private operator fun Vector3f.plus(x: Vector3f): Vector3f {
    return this.add(x, Vector3f())
}

private operator fun Vector3f.minus(x: Vector3f): Vector3f {
    return this.sub(x, Vector3f())
}
*/
