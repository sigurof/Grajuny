package no.sigurof.grajuny.manager

import no.sigurof.grajuny.resource.BillboardResource
import no.sigurof.grajuny.resource.mesh.Loader

object BillboardManager {

    fun getBillboardResource(): BillboardResource {
        return BillboardResource(
            Loader.createVao(),
            4
        )
    }
}