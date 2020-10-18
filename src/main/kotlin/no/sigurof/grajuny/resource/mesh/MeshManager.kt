package no.sigurof.grajuny.resource.mesh

object MeshManager {
    private val sources = mutableMapOf(
        "cube" to "/model/primitives/cube.obj",
        "sphere" to "/model/primitives/sphere.obj",
        "lowp-sphere" to "/model/primitives/lowp-sphere.obj",
        "torus" to "/model/primitives/torus.obj",
        "cylinder" to "/model/primitives/cylinder.obj",
        "dragon" to "/model/stamford-dragon/dragon.obj",
        "stall" to "/model/stall/stall.obj"
    )
    private val meshes = mutableMapOf<String, Mesh>()

    fun getMesh(name: String): Mesh {
        meshes[name] ?: let {
            meshes[name] = Loader.meshLoadToVao(
//     TODO Optimization idea: pass Float ant Int buffers directly here
                ParsedMesh.from(
                    sources[name] ?: error("Couldn't find mesh associated with identifier \"$name\"")
                ).atCenterOfMass()
            )
        }
        return meshes[name]!!
    }

    fun cleanUp() {
        Loader.cleanUp()
    }
}