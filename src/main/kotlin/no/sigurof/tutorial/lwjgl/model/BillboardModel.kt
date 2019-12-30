package no.sigurof.tutorial.lwjgl.model

import no.sigurof.tutorial.lwjgl.engine.Loader
import no.sigurof.tutorial.lwjgl.entity.Camera
import no.sigurof.tutorial.lwjgl.entity.Entity
import no.sigurof.tutorial.lwjgl.entity.Light
import no.sigurof.tutorial.lwjgl.shaders.BillboardShader
import no.sigurof.tutorial.lwjgl.utils.Maths
import org.lwjgl.opengl.GL11

class BillboardModel : Model {

    private val rawModel: RawModel = RawModel(Loader.createVao(), 4)
    private val shader: BillboardShader = BillboardShader
    override fun render(
        entities: List<Entity>,
        light: Light,
        camera: Camera,
        fov: Float,
        nearPlane: Float,
        farPlane: Float
    ) {

        shader.start()
        shader.bindVertAttrArrayAndVao(rawModel.vao)
        shader.loadViewMatrix(Maths.createViewMatrix(camera))
        shader.loadProjectionMatrix(Maths.createProjectionMatrix(fov, nearPlane, farPlane))
        for (entity in entities) {
            shader.loadPos(entity.position) // bør gjøres per modell, ikke per billboard
            shader.loadSphereRadius(entity.scale.x)
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, rawModel.vertexCount)
        }
        shader.unbindVertAttrArrayAndVao()
        shader.stop()
    }
    override fun cleanShader() {
        shader.cleanUp()
    }
}