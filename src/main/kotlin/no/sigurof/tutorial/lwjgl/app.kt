package no.sigurof.tutorial.lwjgl

import no.sigurof.tutorial.lwjgl.engine.DisplayManager
import no.sigurof.tutorial.lwjgl.engine.Loader
import no.sigurof.tutorial.lwjgl.engine.Renderer
import no.sigurof.tutorial.lwjgl.entity.Camera
import no.sigurof.tutorial.lwjgl.entity.Entity
import no.sigurof.tutorial.lwjgl.entity.SphereBillboard
import no.sigurof.tutorial.lwjgl.model.TexturedModel
import no.sigurof.tutorial.lwjgl.shaders.BillboardShader
import no.sigurof.tutorial.lwjgl.shaders.TextureShader
import no.sigurof.tutorial.lwjgl.textures.Texture
import no.sigurof.tutorial.lwjgl.utils.ObjLoader
import org.joml.Vector3f
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL30.glBindVertexArray
import org.lwjgl.opengl.GL30.glGenVertexArrays
import java.lang.System.currentTimeMillis
import java.lang.System.load


fun main() {
    thinmatrix()
}

fun billboard() {
/*
    DisplayManager.FPS = 3
    DisplayManager.createDisplay()
    val vao = glGenVertexArrays()
    glBindVertexArray(vao)
    val shader = BillboardShader()
    val billboard = SphereBillboard()
    val camera = Camera(window = DisplayManager.window ?: error("Need to have a window!"))
    val renderer = Renderer()
    while (DisplayManager.isOpen()) {
        DisplayManager.eachFrameDo {
            //            glEnable(GL_DEPTH_TEST)
            glClearColor(0.2f, 0.3f, 0.1f, 1.0f)
            glClear(GL_COLOR_BUFFER_BIT)
            println(currentTimeMillis())
            shader.loadViewMatrix(camera)
//            shader.load
            billboard.render(shader)
        }
    }
    glBindVertexArray(0)

    DisplayManager.closeDisplay()
*/
}

fun thinmatrix() {
    val scale = Vector3f(1f, 1f, 1f)
    val eulerAngles = Vector3f(0f, 0f, 0f)
    val position = Vector3f(0f, 0f, -1f)


    // Open the window
    DisplayManager.createDisplay()


    // Make an object
    val loader = Loader()
    val objLoader = ObjLoader()
    val model = objLoader.loadObjModel("src/main/resources/model/stall/stall.obj", loader)
    val texture = Texture(loader.loadTexture("src/main/resources/model/stall/stall-texture.png"))
    val texturedModel = TexturedModel(model, texture)
    val entity = Entity(texturedModel, position, eulerAngles, scale)

    val camera = Camera(window = DisplayManager.window ?: error("Need to have a window!"))
    val shader = TextureShader()
    // TODO Dumt at både app.kt og Renderer håndterer camera samtidig.
    //  Burde kanskje heller satse på noe som
    //  camera.uploadViewMatrix(shader)
    //  renderer.uploadProjectionMatrix(shader)
    //  entity.uploadTransformationMatrix(shader)
    //  Shader vil ikke kunne endres fra disse funksjonene, da alle shaders felter er val
    val renderer = Renderer(shader, camera)

    DisplayManager.FPS = 60
    while (DisplayManager.isOpen()) {
        DisplayManager.eachFrameDo {
            entity.increaseRotation(Vector3f(-0.01f, -0.02f, -0.021f))
            camera.move()
            renderer.prepare()
            renderer.render(entity)
        }
    }
    renderer.stop()
    loader.cleanUp()
    DisplayManager.closeDisplay()
}

var vertices = floatArrayOf(
    -0.5f, 0.5f, -0.5f,
    -0.5f, -0.5f, -0.5f,
    0.5f, -0.5f, -0.5f,
    0.5f, 0.5f, -0.5f,
    -0.5f, 0.5f, 0.5f,
    -0.5f, -0.5f, 0.5f,
    0.5f, -0.5f, 0.5f,
    0.5f, 0.5f, 0.5f,
    0.5f, 0.5f, -0.5f,
    0.5f, -0.5f, -0.5f,
    0.5f, -0.5f, 0.5f,
    0.5f, 0.5f, 0.5f,
    -0.5f, 0.5f, -0.5f,
    -0.5f, -0.5f, -0.5f,
    -0.5f, -0.5f, 0.5f,
    -0.5f, 0.5f, 0.5f,
    -0.5f, 0.5f, 0.5f,
    -0.5f, 0.5f, -0.5f,
    0.5f, 0.5f, -0.5f,
    0.5f, 0.5f, 0.5f,
    -0.5f, -0.5f, 0.5f,
    -0.5f, -0.5f, -0.5f,
    0.5f, -0.5f, -0.5f,
    0.5f, -0.5f, 0.5f
)

var uvs = floatArrayOf(
    0f,
    0f,
    0f,
    1f,
    1f,
    1f,
    1f,
    0f,
    0f,
    0f,
    0f,
    1f,
    1f,
    1f,
    1f,
    0f,
    0f,
    0f,
    0f,
    1f,
    1f,
    1f,
    1f,
    0f,
    0f,
    0f,
    0f,
    1f,
    1f,
    1f,
    1f,
    0f,
    0f,
    0f,
    0f,
    1f,
    1f,
    1f,
    1f,
    0f,
    0f,
    0f,
    0f,
    1f,
    1f,
    1f,
    1f,
    0f
)

var indices = intArrayOf(
    0, 1, 3,
    3, 1, 2,
    4, 5, 7,
    7, 5, 6,
    8, 9, 11,
    11, 9, 10,
    12, 13, 15,
    15, 13, 14,
    16, 17, 19,
    19, 17, 18,
    20, 21, 23,
    23, 21, 22
)