package classesandobjects

import java.util.function.BinaryOperator
import java.util.function.IntBinaryOperator

/**
 * Enum Classes
 *
 * Each enum constant is an instance of the enum class(good to know :P)
 */
fun main() {
    println(IntArithmetics.PLUS.apply(12, 34))
    println(IntArithmetics.MULTIPLY.applyAsInt(12, 10))
}

enum class TrafficLights {
    GREEN, YELLOW, RED
}

/**
 * Connecting values to constants via the constructor(This is really useful)
 */
enum class Color(val rgb: Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF)
}

/**
 * Using interfaces with enums
 */
enum class IntArithmetics : BinaryOperator<Int>, IntBinaryOperator {
    PLUS {
        /**
         * separate ones for each entry within its anonymous class
         */
        override fun apply(t: Int, u: Int): Int = t + u
    },
    MULTIPLY {
        /**
         * separate ones for each entry within its anonymous class
         */
        override fun apply(t: Int, u: Int): Int = t * u
    };

    /**
     * single interface members implementation for all of the entries
     */
    override fun applyAsInt(t: Int, u: Int) = apply(t, u)// this is coming from IntBinaryOperator
}