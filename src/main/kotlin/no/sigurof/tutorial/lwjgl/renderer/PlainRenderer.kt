package no.sigurof.tutorial.lwjgl.renderer

import no.sigurof.tutorial.lwjgl.context.DefaultSceneContext
import no.sigurof.tutorial.lwjgl.entity.obj.PlainObject
import no.sigurof.tutorial.lwjgl.resource.MeshResource
import no.sigurof.tutorial.lwjgl.shaders.settings.impl.PlainShaderSettings

class PlainRenderer constructor(
    private val resource: MeshResource,
    private var objects: List<PlainObject> = mutableListOf()
) : Renderer {
    private val shader = PlainShaderSettings
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