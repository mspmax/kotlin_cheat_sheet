package classesandobjects

/**
 * Interfaces
 *  - Can contain abstract methods/properties, as well as method/property implementations
 *  - Property with accessor implementations are not abstract
 *  - Can not contain state (the difference between abstract classes)
 */
fun main() {

}

/**
 * All the members are abstract by default, except for property accessor implementations of course
 */
interface MyInterface {
    var property: String // no need to initialise as no state in interfaces
    val propertyReadOnly: String

    val propertyReadOnlyWithImplementation: String //  not abstract
        get() = "foo"

    var propertyWithImplementation: String
        get() = ""
        set(value) {}

    fun bar()
    fun foo() {
        // optional body
    }
}

interface MySecondInterface {
    fun test()
}

/**
 * Implementing an Interface is no brainier, compiler will take care of everything :)
 */
class TestChild : MyInterface, MySecondInterface {
    override var property: String
        get() = "do your implementation"
        set(value) {}

    override val propertyReadOnly: String
        get() = "do your implementation here"

    override fun bar() {
        // implement here
    }

    override fun test() {
        // implement here
    }
}

/**
 * Interface inheritance
 */
interface Named {
    val name: String
}

interface AwesomePerson : Named {
    val firstName: String
    val lastName: String

    override val name: String get() = "$firstName $lastName"
}

data class Employee(
    // implementing 'name' is not required
    override val firstName: String,
    override val lastName: String,
    val position: AwesomePerson
) : AwesomePerson

