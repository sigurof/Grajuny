package no.sigurof.grajuny

import no.sigurof.grajuny.engine.CoreEngine
import no.sigurof.grajuny.game.SolarSystemGame


/* Wished features:
- post processing
-- adding glow https://learnopengl.com/Advanced-Lighting/Bloom
- multiple light sources
- camera which always looks at an object
- multi gradient
-- adding select feature
- rendering all billboards in the same shader call
- quaternion camera

* DONE:
- featureless shader for billboard  (can enable and disable diffuse/specular in RegularMaterial)
- Fix the Mesh api
- multiple cameras
- attaching camera to gameobject
*/
fun main() {
    CoreEngine.play { window ->
//        TestGame(window)
        SolarSystemGame(window)
    }
}
