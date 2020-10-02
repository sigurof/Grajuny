package no.sigurof.grajuny.resource

import no.sigurof.grajuny.resource.mesh.Loader
import no.sigurof.grajuny.resource.mesh.MeshManager

object ResourceManager {

    fun getMeshResource(name: String): MeshResource {
        return MeshResource(MeshManager.getMesh(name))
    }

    fun getTexturedMeshResource(meshName: String, textureName: String): TexturedMeshResource {
        val mesh = MeshManager.getMesh(meshName)
        val texture = TextureManager.get(textureName)
        return TexturedMeshResource(texture, mesh)
    }

    fun getBillboardResource(): BillboardResource {
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