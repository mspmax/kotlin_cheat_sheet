package classesandobjects

fun main() {

}

/**
 * Nested classes
 *
 * - not much to write here, below code block is self explanatory
 */

class OuterX {
    private val bar: Int = 1

    class NestedX {
        fun foo() = 2
    }
}

val demo = OuterX.NestedX().foo() // == 2

/**
 * Inner classes
 *
 * - A class may be marked as inner to be able to access members of outer class(Good to know !)
 * - Inner classes carry a reference to an object of an outer class
 */

class OuterY {
    private val bar: Int = 1

    inner class Inner {
        fun foo() = bar // outer member is accessed here
    }
}

val test = OuterY().Inner().foo() // == 1