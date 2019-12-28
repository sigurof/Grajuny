package no.sigurof.tutorial.lwjgl.entity

import no.sigurof.tutorial.lwjgl.shaders.BillboardShader
import org.joml.Vector3f
import org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP
import org.lwjgl.opengl.GL11.glDrawArrays

class SphereBillboard(
    private val pos: Vector3f = Vector3f(0f, 0f, 0f),
    private val radius: Float = 1.0f
) {
    fun render(shader: BillboardShader) {
        shader.loadPos(pos) // bør gjøres per modell, ikke per billboard
        shader.loadSphereRadius(radius)
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4)
    }

}