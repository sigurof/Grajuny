package no.sigurof.grajuny.manager

import no.sigurof.grajuny.resource.TBillboardResource
import no.sigurof.grajuny.resource.mesh.Loader

object TBillboardManager {

    fun getBillboardResource(): TBillboardResource {
        return TBillboardResource(
            Loader.createVao(),
            4
        )
    }
}