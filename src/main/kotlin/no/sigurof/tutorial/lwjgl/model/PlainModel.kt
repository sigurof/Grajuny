package no.sigurof.tutorial.lwjgl.model

import no.sigurof.tutorial.lwjgl.context.DefaultSceneContext
import no.sigurof.tutorial.lwjgl.entity.obj.PlainObject
import no.sigurof.tutorial.lwjgl.mesh.MeshManager
import no.sigurof.tutorial.lwjgl.mesh.Vao
import no.sigurof.tutorial.lwjgl.shaders.settings.impl.PlainShaderSettings

class PlainModel private constructor(
    private val vao: Vao,
    private val shader: PlainShaderSettings,
    private var objects: List<PlainObject> = mutableListOf()
) : Model {

    override fun render(globalContext: DefaultSceneContext) {
        shader.usingVaoDo(vao) {
            globalContext.loadUniforms(shader)
            for (obj in objects) {
                obj.loadUniforms(shader)
                obj.render(vao)
            }
        }
    }

    fun addObjects(objects: List<PlainObject>) = apply { this.objects = ArrayList(objects) }
    override fun cleanShader() {
        shader.cleanUp()
    }

    data class Builder(
        private var vao: Vao? = null,
        private var objects: List<PlainObject> = mutableListOf()
    ) {

        fun withModel(name: String) = apply { this.vao = MeshManager.getMesh(name) }
        fun withObjects(objects: List<PlainObject>) = apply { this.objects = ArrayList(objects) }
        fun build(): PlainModel {
            return PlainModel(
                vao ?: error("Must have model to build textured model"),
                PlainShaderSettings,
                objects
            )
        }
    }
}