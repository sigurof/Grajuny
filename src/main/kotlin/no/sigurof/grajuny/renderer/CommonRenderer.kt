package no.sigurof.grajuny.renderer

import no.sigurof.grajuny.context.GlobalContext
import no.sigurof.grajuny.entity.obj.GameObject
import no.sigurof.grajuny.resource.ResourceGl
import no.sigurof.grajuny.shaders.settings.ShaderSettings

class CommonRenderer<S : ShaderSettings>(
    private val shader: S,
    private val resource: ResourceGl<S>,
    val objects: MutableList<out GameObject<S>> = mutableListOf()
) : Renderer {
    override fun render(globalContext: GlobalContext) {
        shader.usingVaoDo(resource) { // TODO NOT GOOD!! shader -> resource
            globalContext.loadUniforms(shader)
            resource.using(shader){ // TODO NOT GOOD!! resource -> shader
                for (obj in objects) {
                    obj.loadUniforms(shader)
                    resource.render()
                }
            }
        }
    }

    override fun cleanShader() {
        shader.cleanUp()
    }
}