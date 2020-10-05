package no.sigurof.grajuny.restructuring

import org.joml.Matrix4f

class MeshRenderer(
    val mesh: TMeshResource,
    val texture: TTexture,
    val material: TMaterial
) : GameComponent() {
    override var transform: Matrix4f = Matrix4f()

    init {
        RenderingEngine.shaders.add(BasicShader)
    }

    override fun input() {

    }

    override fun upload(shader: Shader, transform: Matrix4f) {
        if (shader is BasicShader) {
            shader.loadTransformationMatrix(transform)
            material.render(shader)
            texture.activate()
            texture.render(shader)
            mesh.activate()
            mesh.render()
            mesh.deactivate()
            texture.deactivate()
        }
    }

    override fun update() {

    }
}