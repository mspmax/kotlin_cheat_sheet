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

/**
 * Sealed classes
 * - used for representing restricted class hierarchies
 * - an extension of enum classes(Enums on steroids !)
 * - each enum constant exists only as a single instance(singleton), whereas a subclass of a sealed class
 *   can have multiple instances which can contain state
 *
 * - NOTE: can have subclasses, but all of them must be declared in the same file
 * - abstract by default, can have abstract members
 * - constructors are private by default, NO non private constructors !!!
 * - becomes handy with 'when expression', if you verify all the cases you don't need to add an else(Yes !!!!!)
 *   and smart casting is just awesome !
 */

sealed class ScreenState {
    object Error : ScreenState()
    object Loading : ScreenState()
    data class Data(val someData: String) : ScreenState() // an example of data class extending other classes
}

fun setScreenState(screenState: ScreenState) {
    when (screenState) {
        is ScreenState.Error -> { /* set error state in the view */
        }
        is ScreenState.Loading -> { /* set loading state in the view */
        }
        is ScreenState.Data -> { // smart cast in action !
            /* hide loading or error states in the view and display data*/
            // update the UI here
            //sometextView.text = screenState.someData
        }
    }
}

private const val KEY_A = 1000L
private const val KAY_B = 250L

class FooX {
    companion object {
        private const val KEY_A = 1000L
        private const val KAY_B = 250L
    }
}