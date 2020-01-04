package no.sigurof.tutorial.lwjgl.resource.mesh

import no.sigurof.tutorial.lwjgl.resource.MeshResource
import no.sigurof.tutorial.lwjgl.resource.TextureManager
import no.sigurof.tutorial.lwjgl.resource.TexturedMeshResource

object MeshManager {
    private val sources = mutableMapOf(
        "cube" to "src/main/resources/model/primitives/cube.obj",
        "sphere" to "src/main/resources/model/primitives/sphere.obj",
        "lowp-sphere" to "src/main/resources/model/primitives/lowp-sphere.obj",
        "torus" to "src/main/resources/model/primitives/torus.obj",
        "cylinder" to "src/main/resources/model/primitives/cylinder.obj",
        "dragon" to "src/main/resources/model/stamford-dragon/dragon.obj",
        "stall" to "src/main/resources/model/stall/stall.obj"
    )
    private val meshes = mutableMapOf<String, Mesh>()

    fun getMeshResource(name: String): MeshResource {
        return MeshResource(getMesh(name))
    }

    fun getTexturedMeshResource(meshName: String, textureName: String): TexturedMeshResource {
        val mesh = getMesh(meshName)
        val texture = TextureManager.get(textureName)
        return TexturedMeshResource(mesh, texture)
    }

    private fun getMesh(name: String): Mesh {
        meshes[name] ?: let {
            meshes[name] = Loader.loadToVao(
//     TODO Optimization idea: pass Float ant Int buffers directly here
                ParsedMesh.from(
                    sources[name] ?: error("Couldn't find mesh associated with identifier \"$name\"")
                )
            )
        }
        return meshes[name]!!
    }

    fun cleanUp() {
        Loader.cleanUp()
    }
}