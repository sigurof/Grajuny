package no.sigurof.tutorial.lwjgl.renderer

import no.sigurof.tutorial.lwjgl.context.DefaultSceneContext
import no.sigurof.tutorial.lwjgl.entity.Camera
import no.sigurof.tutorial.lwjgl.entity.obj.SphereBillboardObject
import no.sigurof.tutorial.lwjgl.resource.BillboardResource
import no.sigurof.tutorial.lwjgl.resource.mesh.Loader
import no.sigurof.tutorial.lwjgl.shaders.settings.impl.BillboardShaderSettings

class BillboardRenderer(val camera: Camera) : Renderer {
    private val billboardResource: BillboardResource = BillboardResource(Loader.createVao(), 4, camera)
    private var objects: List<SphereBillboardObject> = mutableListOf()
    override fun render(globalContext: DefaultSceneContext) {
        BillboardShaderSettings.usingVaoDo(billboardResource) {
            globalContext.loadUniforms(BillboardShaderSettings)
            billboardResource.prepare()
            for (obj in objects) {
                obj.loadUniforms(BillboardShaderSettings)
                billboardResource.render()
            }
        }
    }

    fun addObjects(objects: List<SphereBillboardObject>) = apply { this.objects = ArrayList(objects) }
    override fun cleanShader() {
        BillboardShaderSettings.cleanUp()
    }
}