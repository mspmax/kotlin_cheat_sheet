package classesandobjects

/**
 * Visibility Modifiers
 *
 * - The way visibility modifiers work is different from packages to classes/interfaces
 * - Local variables, functions and classes can not have visibility modifiers.
 */
fun main() {
}

/**
 * Package Level
 *
 *  - private : only visible inside the file containing the declaration
 *  - protected : NOT available
 *  - internal : visible everywhere in the same module
 *  - public : default one, if not specified explicitly. Visible everywhere
 *
 */
private fun foo() {} // visible inside example.kt

var bar: Int = 5 // property is visible everywhere
    private set         // setter is visible only in VisibilityModifiers.kt

internal val baz = 6    // visible inside the same module

/**
 * Classes/Interfaces Level
 *
 *  - private : visible inside this class only (including all its members).
 *              NOTE: Even in nested classes if the members are private then it would
 *              not be visible for the outer class
 *
 *  - protected : same as private but visible to subclasses
 *
 *  - internal : any client inside this module(eg: a Maven project or an Android module) who sees the declaring class sees
 *               its internal members
 *
 *  - public :  default one, if not specified explicitly. Any client who sees the declaring
 *              class sees its public members.
 */

open class Outer {
    private val a = 1
    protected open val b = 2
    internal val c = 3
    val d = 4  // public by default

    protected class Nested {
        val e: Int = 5
    }
}

class Subclass : Outer() {
    // a is not visible
    // b, c and d are visible
    // Nested and e are visible

    override val b = 5   // 'b' is protected
}

class Unrelated(o: Outer) {
    // o.a, o.b are not visible
    // o.c and o.d are visible (same module)
    // Outer.Nested is not visible, and Nested::e is not visible either
}