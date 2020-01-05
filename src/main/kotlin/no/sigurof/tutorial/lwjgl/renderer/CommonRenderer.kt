package no.sigurof.tutorial.lwjgl.renderer

import no.sigurof.tutorial.lwjgl.context.GlobalContext
import no.sigurof.tutorial.lwjgl.entity.obj.GameObject
import no.sigurof.tutorial.lwjgl.resource.ResourceGl
import no.sigurof.tutorial.lwjgl.shaders.settings.ShaderSettings

class CommonRenderer<S : ShaderSettings>(
    private val shader: S,
    private val resource: ResourceGl<S>,
    private var objects: MutableList<out GameObject<S>> = mutableListOf()
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