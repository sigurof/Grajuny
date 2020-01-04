package no.sigurof.tutorial.lwjgl.renderer

import no.sigurof.tutorial.lwjgl.context.DefaultSceneContext
import no.sigurof.tutorial.lwjgl.entity.obj.PlainObject
import no.sigurof.tutorial.lwjgl.resource.MeshResource
import no.sigurof.tutorial.lwjgl.resource.mesh.MeshManager
import no.sigurof.tutorial.lwjgl.shaders.settings.impl.PlainShaderSettings

class PlainRenderer private constructor(
    private val meshResource: MeshResource,
    private val shader: PlainShaderSettings,
    private var objects: List<PlainObject> = mutableListOf()
) : Renderer {

    override fun render(globalContext: DefaultSceneContext) {
        shader.usingVaoDo(meshResource) {
            globalContext.loadUniforms(shader)
            for (obj in objects) {
                obj.loadUniforms(shader)
                meshResource.render()
            }
        }
    }

    fun addObjects(objects: List<PlainObject>) = apply { this.objects = ArrayList(objects) }
    override fun cleanShader() {
        shader.cleanUp()
    }

    data class Builder(
        private var vao: MeshResource? = null,
        private var objects: List<PlainObject> = mutableListOf()
    ) {

        fun withModel(name: String) = apply { this.vao = MeshManager.getMeshResource(name) }
        fun withObjects(objects: List<PlainObject>) = apply { this.objects = ArrayList(objects) }
        fun build(): PlainRenderer {
            return PlainRenderer(
                vao ?: error("Must have renderer to build textured renderer"),
                PlainShaderSettings,
                objects
            )
        }
    }
}