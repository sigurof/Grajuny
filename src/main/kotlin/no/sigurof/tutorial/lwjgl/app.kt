package no.sigurof.tutorial.lwjgl

import no.sigurof.tutorial.lwjgl.engine.DisplayManager
import no.sigurof.tutorial.lwjgl.engine.Loader
import no.sigurof.tutorial.lwjgl.engine.MasterRenderer
import no.sigurof.tutorial.lwjgl.entity.Camera
import no.sigurof.tutorial.lwjgl.entity.Entity
import no.sigurof.tutorial.lwjgl.entity.Light
import no.sigurof.tutorial.lwjgl.mesh.MeshManager
import no.sigurof.tutorial.lwjgl.model.TexturedModel
import no.sigurof.tutorial.lwjgl.shaders.TextureShader
import no.sigurof.tutorial.lwjgl.textures.Texture
import no.sigurof.tutorial.lwjgl.mesh.ObjLoader
import no.sigurof.tutorial.lwjgl.utils.randomVector3f
import org.joml.Vector3f
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
    val position = Vector3f(0f, 0f, 0f)
    DisplayManager.createDisplay()

//    val model = MeshManager.get("stamford-dragon")
    val model = MeshManager.get("cube")

    val texture = Texture(
        Loader.loadTexture("src/main/resources/model/default-texture.png"),
        1.0f, 1.0f
    )

    val texturedModel = TexturedModel(model, texture)
    val entities = mutableListOf<Entity>()
    val light = Light(Vector3f(0f, 10f, 0f), Vector3f(0.8f, 0.8f, 0.9f), 0.0f)
    entities.add(Entity(texturedModel, position, eulerAngles, scale))
    entities.add(Entity(texturedModel, light.position, eulerAngles, scale))


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
    Loader.cleanUp()
    DisplayManager.closeDisplay()
}
