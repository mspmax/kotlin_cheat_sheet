package classesandobjects

import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JFrame

/**
 * Objects
 *
 *  - anonymous objects can be used as types only in local and private declarations
 *  - declare it when you wanna create static objects( well that's partially true cause at runtime
 *    those are still instance members of real objects. Kotlin represents package-level functions as static methods)
 *  - Kotlin can also generate static methods for functions defined in named objects or companion objects
 *    if you annotate those functions as @JvmStatic
 */

fun main() {

}

/**
 * Object Expressions
 *  - object expressions are executed (and initialized) immediately, where they are used (pretty obvious right ??)
 */
class TestObjects {
    val window: JFrame = JFrame()

    val listeners = window.addMouseListener(object : MouseAdapter() {

    })
}

/**
 * When having multiple supertypes can be specified as a comma-separated list
 */
open class A2(x: Int) {
    open val y: Int = x
}

interface B2 {
    fun methodB2()
}

val ab: A2 = object : A2(1), B2 {
    override fun methodB2() {
        println("method B2")
    }

    override val y = 15
}

/**
 * If it's just an object", with no nontrivial supertypes
 */
fun fooAd() {
    val adHoc = object {
        var x: Int = 0
        var y: Int = 0
    }
    print(adHoc.x + adHoc.y)
}

class C2 {
    // Private function, so the return type is the anonymous object type
    private fun foo() = object {
        val x: String = "x"
    }

    // Public function, so the return type is Any
    fun publicFoo() = object {
        val x: String = "x"
    }

    fun bar() {
        val x1 = foo().x        // Works
        //val x2 = publicFoo().x  // ERROR: Unresolved reference 'x' // this is weired !!!!
    }
}

/**
 * Object Declarations -  Singleton - (it's really easy to declare singletons now !)
 *  - object declarations are initialized lazily, when accessed for the first time
 */

object DataProviderManager {

    fun providerFunction(provider: String): String {
        return "test default"
    }

    val property: Collection<String> = listOf("Test_0", "Test_1", "Test_2")
}

/**
 * How to use it
 */
val testObject = DataProviderManager.providerFunction("Test") // basically access from the object name directly

/**
 * Can extend
 */

object DefaultListener : MouseAdapter() {
    override fun mouseClicked(e: MouseEvent) {}

    override fun mouseEntered(e: MouseEvent) {}
}

/**
 * Companion Objects -  more like static blocks in Java
 *  - a companion object is initialized when the corresponding class is loaded (resolved),
 *    matching the semantics of a Java static initializer
 */
class MyClass {
    companion object Factory { // here the companion object is named, but it's not really necessary
        fun create(): MyClass = MyClass()
    }
}

/**
 * Usage - The name of a class used by itself (not as a qualifier to another name) acts as a reference
 * to the companion object of the class (whether named or not)
 *
 * NOTE: when accessing from Java it has to be like this MyClass.Companion.create();
 * This is because Java always decompile companion objects to a final class called Companion
 * If we name this it will be replaced by the name instead. So be careful, specially when you're writing
 * libraries with kotlin for the usage of both kotlin and Java these should be taken into consideration
 */
val myClass = MyClass.create()
val myClass2 = MyClass.Factory.create()