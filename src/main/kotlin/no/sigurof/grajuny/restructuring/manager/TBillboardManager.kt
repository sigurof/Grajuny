package no.sigurof.grajuny.restructuring.manager

import no.sigurof.grajuny.resource.mesh.Loader
import no.sigurof.grajuny.restructuring.resource.TBillboardResource

object TBillboardManager {

    fun getBillboardResource(): TBillboardResource {
        return TBillboardResource(
            Loader.createVao(),
            4
        )
    }
}