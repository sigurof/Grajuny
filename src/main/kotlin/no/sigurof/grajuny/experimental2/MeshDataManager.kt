package no.sigurof.grajuny.experimental2

import no.sigurof.grajuny.resource.mesh.Loader
import no.sigurof.grajuny.resource.mesh.ParsedMesh

object MeshDataManager {
    private val sources = mutableMapOf(
        "cube" to "/model/primitives/cube.obj",
        "sphere" to "/model/primitives/sphere.obj",
        "lowp-sphere" to "/model/primitives/lowp-sphere.obj",
        "torus" to "/model/primitives/torus.obj",
        "cylinder" to "/model/primitives/cylinder.obj",
        "dragon" to "/model/stamford-dragon/dragon.obj",
        "stall" to "/model/stall/stall.obj"
    )
    private val meshes = mutableMapOf<String, MeshData>()

    fun getMeshData(name: String): MeshData {
        meshes[name] ?: let {
            meshes[name] = Loader.meshDataLoadToVao(
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