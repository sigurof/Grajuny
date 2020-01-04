package no.sigurof.tutorial.lwjgl.renderer

import no.sigurof.tutorial.lwjgl.context.DefaultSceneContext
import no.sigurof.tutorial.lwjgl.entity.obj.PlainObject
import no.sigurof.tutorial.lwjgl.resource.TexturedMeshResource
import no.sigurof.tutorial.lwjgl.shaders.settings.impl.TextureShaderSettings

class TexturedRenderer constructor(
    private val resource: TexturedMeshResource,
    private var objects: List<PlainObject>
) : Renderer {
    private val shader = TextureShaderSettings
    override fun render(globalContext: DefaultSceneContext) {
        shader.usingVaoDo(resource) {
            globalContext.loadUniforms(shader)
            resource.prepare()
            for (obj in objects) {
                obj.loadUniforms(shader)
                resource.render()
            }
        }
    }

    override fun cleanShader() {
        shader.cleanUp()
    }
}