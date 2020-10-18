package no.sigurof.grajuny.utils

import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

internal class MathsTest : StringSpec() {
    init {
        "clamp" should {
            val low = 1f
            val high = 2f
            "clamp upwards"{
                Maths.clamp(-10f, low, high) shouldBe low
            }
            "clamp downwards"{
                Maths.clamp(10f, low, high) shouldBe high
            }
            "keep at low" {
                Maths.clamp(low, low, high) shouldBe low
            }
            "keep at high" {
                Maths.clamp(high, low, high) shouldBe high
            }
            "keep between low and high"{
                val value = low + 0.3f * (high - low)
                Maths.clamp(value, low, high) shouldBe value
            }
        }
    }
}