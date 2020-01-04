package no.sigurof.tutorial.lwjgl.model

import no.sigurof.tutorial.lwjgl.context.DefaultSceneContext
import no.sigurof.tutorial.lwjgl.entity.obj.SphereBillboardObject
import no.sigurof.tutorial.lwjgl.mesh.Vao
import no.sigurof.tutorial.lwjgl.resource.mesh.Loader
import no.sigurof.tutorial.lwjgl.shaders.settings.impl.BillboardShaderSettings

class BillboardModel : Model {
    private val vao: Vao =
        Vao(Loader.createVao(), 4)
    private var objects: List<SphereBillboardObject> = mutableListOf()
    override fun render(globalContext: DefaultSceneContext) {
        BillboardShaderSettings.usingVaoDo(vao) {
            globalContext.loadUniforms(BillboardShaderSettings)
            BillboardShaderSettings.loadCameraPos(globalContext.camera.pos)
            for (obj in objects) {
                obj.loadUniforms(BillboardShaderSettings)
                obj.render(vao)
            }
        }
    }

    fun addObjects(objects: List<SphereBillboardObject>) = apply { this.objects = ArrayList(objects) }
    override fun cleanShader() {
        BillboardShaderSettings.cleanUp()
    }
}