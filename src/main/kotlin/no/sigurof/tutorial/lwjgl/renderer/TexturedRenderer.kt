package no.sigurof.tutorial.lwjgl.renderer

import no.sigurof.tutorial.lwjgl.context.DefaultSceneContext
import no.sigurof.tutorial.lwjgl.entity.obj.PlainObject
import no.sigurof.tutorial.lwjgl.resource.TexturedMeshResource
import no.sigurof.tutorial.lwjgl.resource.mesh.MeshManager
import no.sigurof.tutorial.lwjgl.shaders.settings.impl.TextureShaderSettings

class TexturedRenderer private constructor(
    private val texturedMeshResource: TexturedMeshResource,
    private var objects: List<PlainObject>
) : Renderer {

    override fun render(globalContext: DefaultSceneContext) {
        TextureShaderSettings.usingVaoDo(texturedMeshResource) {
            globalContext.loadUniforms(TextureShaderSettings)
            texturedMeshResource.prepare()
            for (obj in objects) {
                obj.loadUniforms(TextureShaderSettings)
                texturedMeshResource.render()
            }
        }
    }

    override fun cleanShader() {
        TextureShaderSettings.cleanUp()
    }

    data class Builder(
        private var texturedMeshResource: TexturedMeshResource? = null,
        private var texName: String = "default",
        private var objects: List<PlainObject> = mutableListOf()
    ) {
        fun withTexture(name: String) = apply { this.texName = name }
        fun withModel(name: String) =
            apply { this.texturedMeshResource = MeshManager.getTexturedMeshResource(name, texName) }

        fun withObjects(objects: List<PlainObject>) = apply { this.objects = ArrayList(objects) }
        fun build(): TexturedRenderer {
            return TexturedRenderer(
                texturedMeshResource ?: error("Must have renderer to build textured renderer"),
                objects
            )
        }
    }
}