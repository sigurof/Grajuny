package no.sigurof.tutorial.lwjgl.renderer

import no.sigurof.tutorial.lwjgl.context.DefaultSceneContext
import no.sigurof.tutorial.lwjgl.entity.Camera
import no.sigurof.tutorial.lwjgl.entity.obj.SphereBillboardObject
import no.sigurof.tutorial.lwjgl.resource.TexturedBillboardResource
import no.sigurof.tutorial.lwjgl.resource.mesh.Loader
import no.sigurof.tutorial.lwjgl.shaders.settings.impl.BillboardShaderSettings

class TexturedBillboardRenderer(
    camera: Camera,
    tex: Int,
    private var objects: List<SphereBillboardObject> = mutableListOf()
) : Renderer {
    private val vao: TexturedBillboardResource = TexturedBillboardResource(Loader.createVao(), 4, camera, tex)
    override fun render(globalContext: DefaultSceneContext) {
        BillboardShaderSettings.usingVaoDo(vao) {
            globalContext.loadUniforms(BillboardShaderSettings)
            vao.prepare()
            for (obj in objects) {
                obj.loadUniforms(BillboardShaderSettings)
                vao.render()
            }
        }
    }

    fun addObjects(objects: List<SphereBillboardObject>) = apply { this.objects = ArrayList(objects) }
    override fun cleanShader() {
        BillboardShaderSettings.cleanUp()
    }
}