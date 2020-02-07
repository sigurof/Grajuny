package no.sigurof.grajuny.experimental

import no.sigurof.grajuny.context.DefaultSceneContext
import no.sigurof.grajuny.entity.obj.PlainObject
import no.sigurof.grajuny.resource.TexturedMeshResource
import no.sigurof.grajuny.shaders.ShaderManager
import no.sigurof.grajuny.shaders.settings.impl.StandardShader

class PlainObjectTexturedMeshStandard(
    private val resource: TexturedMeshResource,
    private val objects: MutableList<PlainObject>
) : ShaderInterface<DefaultSceneContext> {

    private val shader = StandardShader

    override fun render(context: DefaultSceneContext) {
        ShaderManager.useShader(shader.program)
        context.loadUniforms(shader)
        resource.activate()
        shader.loadUseTexture(true)
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