package no.sigurof.grajuny.utils

class CyclicCounter private constructor(
    val max: Int,
    current: Int
) {

    var current: Int = current
        private set

    fun incrementByOne() {
        incrementBy(1)
    }

    fun incrementBy(num: Int) {
        current = incrementedBy(num)
    }

    fun incrementedBy(num: Int): Int {
        return (current + num) % max
    }

    fun decrementedBy(num: Int): Int {
        return (current + max - num) % max
    }

    fun peekNext(): Int {
        return incrementedBy(1)
    }

    fun peekLast(): Int {
        return decrementedBy(1)
    }

    companion object {

        fun exclusiveMax(max: Int): CyclicCounter {
            return CyclicCounter(
                max = max,
                current = 0
            )
        }
    }
}