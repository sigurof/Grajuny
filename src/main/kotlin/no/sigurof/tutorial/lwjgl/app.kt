package no.sigurof.tutorial.lwjgl

import no.sigurof.tutorial.lwjgl.engine.DisplayManager
import no.sigurof.tutorial.lwjgl.engine.Loader
import no.sigurof.tutorial.lwjgl.engine.MasterRenderer
import no.sigurof.tutorial.lwjgl.engine.Renderer
import no.sigurof.tutorial.lwjgl.entity.Camera
import no.sigurof.tutorial.lwjgl.entity.Entity
import no.sigurof.tutorial.lwjgl.entity.Light
import no.sigurof.tutorial.lwjgl.entity.SphereBillboard
import no.sigurof.tutorial.lwjgl.model.TexturedModel
import no.sigurof.tutorial.lwjgl.shaders.BillboardShader
import no.sigurof.tutorial.lwjgl.shaders.TextureShader
import no.sigurof.tutorial.lwjgl.textures.Texture
import no.sigurof.tutorial.lwjgl.utils.ObjLoader
import no.sigurof.tutorial.lwjgl.utils.randomVector3f
import org.joml.Vector3f
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL30.glBindVertexArray
import org.lwjgl.opengl.GL30.glGenVertexArrays
import java.lang.System.currentTimeMillis
import java.lang.System.load
import kotlin.random.Random


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
    val position = Vector3f(0f, -7f, -20f)


    // Open the window
    DisplayManager.createDisplay()


    // Make an object
    val loader = Loader()
    val objLoader = ObjLoader()
    val model = objLoader.loadObjModel("src/main/resources/model/stamford-dragon/dragon.obj", loader)
    val texture = Texture(
        loader.loadTexture("src/main/resources/model/stamford-dragon/white.png"),
        10f, 5f
    )
    val texturedModel = TexturedModel(model, texture)
    val entity = Entity(texturedModel, position, eulerAngles, scale)
    val entities = mutableListOf<Entity>()
    for (i in 0..60) {
        val scale = Random.nextFloat()
        entities.add(
            Entity(
                texturedModel,
                randomVector3f(),
                randomVector3f(),
                Vector3f(scale, scale, scale)
            )
        )
    }
    val light = Light(Vector3f(0f, 0f, -15f), Vector3f(0.8f, 0.8f, 0.9f), 0.2f)

    val camera = Camera(window = DisplayManager.window ?: error("Need to have a window!"))
    val shader = TextureShader()
    // TODO Dumt at både app.kt og Renderer håndterer camera samtidig.
    //  Burde kanskje heller satse på noe som
    //  camera.uploadViewMatrix(shader)
    //  renderer.uploadProjectionMatrix(shader)
    //  entity.uploadTransformationMatrix(shader)
    //  Shader vil ikke kunne endres fra disse funksjonene, da alle shaders felter er val
    val masterRenderer = MasterRenderer(shader, camera, light)

    DisplayManager.FPS = 60
    while (DisplayManager.isOpen()) {
        DisplayManager.eachFrameDo {
            camera.move()
            for (entity in entities) {
                masterRenderer.processEntity(entity)
            }
            masterRenderer.render()
        }
    }
    loader.cleanUp()
    DisplayManager.closeDisplay()
}
