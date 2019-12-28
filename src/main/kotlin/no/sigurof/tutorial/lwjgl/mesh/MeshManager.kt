package no.sigurof.tutorial.lwjgl.mesh

import no.sigurof.tutorial.lwjgl.model.RawModel

class MeshManager {
    companion object {
        private val loader: ObjLoader = ObjLoader()
        private val meshes = mutableMapOf(
            "cube" to "src/main/resources/model/primitives/cube.obj",
            "sphere" to "src/main/resources/model/primitives/sphere.obj",
            "lowp-sphere" to "src/main/resources/model/primitives/lowp-sphere.obj",
            "torus" to "src/main/resources/model/primitives/torus.obj",
            "cylinder" to "src/main/resources/model/primitives/cylinder.obj",
            "dragon" to "src/main/resources/model/stamford-dragon/dragon.obj",
            "stall" to "src/main/resources/model/stall/stall.obj"
        )

        fun get(name: String): RawModel {
            return meshes.get(name)?.let {
                return loader.loadObjModel(it)
            } ?: error("Couldn't find mesh associated with identifier \"$name\"")
        }
    }
}