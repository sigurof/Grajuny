package no.sigurof.tutorial.lwjgl.renderer

import no.sigurof.tutorial.lwjgl.context.DefaultSceneContext
import no.sigurof.tutorial.lwjgl.entity.obj.SphereBillboardObject
import no.sigurof.tutorial.lwjgl.resource.BillboardResource
import no.sigurof.tutorial.lwjgl.shaders.settings.impl.BillboardShaderSettings

class BillboardRenderer(
    private val resource: BillboardResource,
    private var objects: List<SphereBillboardObject> = mutableListOf()
) : Renderer {
    private val shader = BillboardShaderSettings
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