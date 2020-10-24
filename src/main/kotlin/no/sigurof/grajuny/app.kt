package no.sigurof.grajuny

import no.sigurof.grajuny.engine.CoreEngine
import no.sigurof.grajuny.game.SolarSystemGame


/* Wished features:
- multi gradient
- quaternion camera
- attaching camera to gameobject
- camera which always looks at an object
- post processing
-- adding glow
-- adding select feature
- rendering all billboards in the same shader call

* DONE:
- featureless shader for billboard  (can enable and disable diffuse/specular in RegularMaterial)
- Fix the Mesh api
- multiple cameras
*/
fun main() {
    CoreEngine.play { window ->
//        TestGame(window)
        SolarSystemGame(window)
    }
}
