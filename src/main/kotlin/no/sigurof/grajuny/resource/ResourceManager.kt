package no.sigurof.grajuny.resource

import no.sigurof.grajuny.entity.Camera
import no.sigurof.grajuny.resource.mesh.Loader
import no.sigurof.grajuny.resource.mesh.MeshManager

object ResourceManager {

    fun getMeshResource(name: String): MeshResource {
        return MeshResource(MeshManager.getMesh(name), listOf(0, 1, 2))
    }

    fun getTexturedMeshResource(meshName: String, textureName: String): TexturedMeshResource {
        val mesh = MeshManager.getMesh(meshName)
        val texture = TextureManager.get(textureName)
        return TexturedMeshResource(texture, mesh, listOf(0, 1, 2))
    }

    fun getBillboardResource(camera: Camera): BillboardResource {
        return BillboardResource(
            Loader.createVao(),
            4
        )
    }

    fun getTexturedBillboardResource(textureName: String): TexturedBillboardResource {
        return TexturedBillboardResource(
            TextureManager.get(textureName),
            Loader.createVao(),
            4
        )
    }
}