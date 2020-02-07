package no.sigurof.grajuny.experimental

import no.sigurof.grajuny.context.DefaultSceneContext
import no.sigurof.grajuny.entity.obj.SphereBillboardObject
import no.sigurof.grajuny.resource.BillboardResource
import no.sigurof.grajuny.shaders.ShaderManager
import no.sigurof.grajuny.shaders.settings.impl.BillboardShaderSettings

class BbdObjBbdResourceBillboard(
    private val resource: BillboardResource,
    private val objects: MutableList<SphereBillboardObject>
) : ShaderInterface<DefaultSceneContext> {

    private val shader = BillboardShaderSettings

    override fun render(context: DefaultSceneContext) {
        ShaderManager.useShader(shader.program)
        context.loadUniforms(shader)
        resource.activate()
        shader.loadUseTexture(false)
        for (obj in objects) {
            obj.loadUniforms(shader)
            resource.render()
        }
        resource.deactivate()
        ShaderManager.stop()
    }

    override fun cleanShader() {
        ShaderManager.cleanUp(shader.program)
    }

}