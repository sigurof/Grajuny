package no.sigurof.grajuny.renderer

import no.sigurof.grajuny.context.GlobalContext
import no.sigurof.grajuny.entity.obj.GameObject
import no.sigurof.grajuny.resource.ResourceGl
import no.sigurof.grajuny.shaders.ShaderManager
import no.sigurof.grajuny.shaders.settings.ShaderSettings

class CommonRenderer<S : ShaderSettings>(
    private val shader: S,
    private val resource: ResourceGl<S>,
    private var objects: MutableList<out GameObject<S>> = mutableListOf()
) : Renderer {
    override fun render(globalContext: GlobalContext) {
        ShaderManager.useShader(shader.program)
        resource.activate(shader)
        globalContext.loadUniforms(shader)
        for (obj in objects) {
            obj.loadUniforms(shader)
            resource.render()
        }
        resource.deactivate(shader)
    }

    override fun cleanShader() {
        ShaderManager.cleanUp(shader.program)
    }
}