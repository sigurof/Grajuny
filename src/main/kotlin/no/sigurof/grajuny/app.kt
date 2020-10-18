package no.sigurof.grajuny

import no.sigurof.grajuny.engine.CoreEngine
import no.sigurof.grajuny.game.SolarSystemGame


/* Wished features:
- multi gradient
- featureless shader for billboard
- quaternion camera
- attaching camera to gameobject
- multiple cameras
- camera which always looks at an object
- post processing
-- adding glow
-- adding select feature
- rendering all billboards in the same shader call
*/
fun main() {
    CoreEngine.play { window ->
//        TestGame(window)
        SolarSystemGame(window)
    }
}
