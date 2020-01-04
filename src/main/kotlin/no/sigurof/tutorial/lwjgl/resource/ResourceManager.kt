package no.sigurof.tutorial.lwjgl.resource

import no.sigurof.tutorial.lwjgl.entity.Camera
import no.sigurof.tutorial.lwjgl.resource.mesh.Loader
import no.sigurof.tutorial.lwjgl.resource.mesh.MeshManager

object ResourceManager {

    fun getMeshResource(name: String): MeshResource {
        return MeshResource(MeshManager.getMesh(name))
    }

    fun getTexturedMeshResource(meshName: String, textureName: String): TexturedMeshResource {
        val mesh = MeshManager.getMesh(meshName)
        val texture = TextureManager.get(textureName)
        return TexturedMeshResource(texture, mesh)
    }

    fun getBillboardResource(camera: Camera): BillboardResource {
        return BillboardResource(
            Loader.createVao(),
            4
        )
    }

    fun getTexturedBillboardResource(textureName: String, camera: Camera): TexturedBillboardResource {
        return TexturedBillboardResource(
            TextureManager.get(textureName),
            Loader.createVao(),
            4
        )
    }
}