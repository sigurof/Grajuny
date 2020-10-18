package no.sigurof.grajuny.color

import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.joml.Vector3f

internal class GradientTest : StringSpec() {

    init {
        "Evaluating a simple gradient with" should {
            val gradient = Gradient(
                start = Vector3f(0f, 0f, 0f),
                end = Vector3f(1f, 1f, 1f)
            )
            "clamping "
            "0 should yield black"{
                gradient.evaluate(0f) shouldBe Vector3f(0f, 0f, 0f)
            }
            "1 should yield white" {
                gradient.evaluate(1f) shouldBe Vector3f(1f, 1f, 1f)
            }
        }
    }

}