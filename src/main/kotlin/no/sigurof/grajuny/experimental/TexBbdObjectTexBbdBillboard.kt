package no.sigurof.grajuny.experimental

import no.sigurof.grajuny.context.DefaultSceneContext
import no.sigurof.grajuny.entity.obj.TexturedBbdSphereObject
import no.sigurof.grajuny.resource.TexturedBillboardResource
import no.sigurof.grajuny.shaders.ShaderManager
import no.sigurof.grajuny.shaders.settings.impl.BillboardShaderSettings

class TexBbdObjectTexBbdBillboard(
    private val resource: TexturedBillboardResource,
    private val objects: MutableList<TexturedBbdSphereObject>
) : ShaderInterface<DefaultSceneContext>{

    private val shader = BillboardShaderSettings

    override fun render(context: DefaultSceneContext) {
        ShaderManager.useShader(shader.program)
        context.loadUniforms(shader)
        resource.activate()
        shader.loadUseTexture(true)
        for (obj in objects){
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