package classesandobjects

/**
 *  - All classes in Kotlin have a common superclass 'Any', that is the default superclass for a
 *    class with no supertypes declared
 *
 *  - According to the official docs, Any is not java.lang.Object! in particular, it does not have any members other than
 *    equals(), hashCode() and toString(). But during Java interoperability 'Any' is converted to java.lang.Object (What ????)
 *    .If this is confusing you can read more here https://kotlinlang.org/docs/reference/java-interop.html#object-methods
 *
 *    Bottom line I would say 'Object' and 'Any' are conceptually the same but not the same implementation under the hood
 *
 *  - Classes are final(not open) by default (Seriously !???)
 *  - During construction of a new instance of a derived class, the base class initialization is done as the first step
 *    (preceded only by evaluation of the arguments for the base class constructor)
 *    and thus happens before the initialization logic of the derived class is run.
 *    read this for more info https://kotlinlang.org/docs/reference/classes.html#derived-class-initialization-order
 *
 *  - When designing a base class, you should avoid using open members in the,
 *   -- constructors
 *   -- property initializer
 *   -- init blocks
 *
 *  - Inside an inner class, accessing the superclass of the outer class is done with the super keyword qualified
 *    with the outer class name: super@Outer:
 */
fun main() {

}

/**
 * Different ways to declare classes in Kotlin
 *  - A class in Kotlin can have a primary constructor and one or more secondary constructors
 *  - The primary constructor cannot contain any code
 *  - Initialization code should be placed in init method
 *  - If the primary constructor does not have any annotations(eg: @Inject) or visibility modifiers(eg: private),
 *    the constructor keyword can be omitted
 *  - initializer blocks( init{} block ) are executed in the same order as they appear in the class body
 */

class Empty // NOTE: In this case a primary constructor with no arguments and with visibility public is generated implicitly

class Person(val firstName: String)

class PersonWithDefaultValues(firstName: String = "John Doe")

class PersonMutableParams(var firstName: String)

class PersonImmuutableParams(val firstName: String)

class PersonWithConstructor constructor(firstName: String) //constructor keyword is optional if it's a simple class

class PersonWithPrivateConstructor private constructor(firstName: String) // NOTE: here the constructor keyword is mandatory

class PersonWithMultiConstructors(firstName: String) {

    private val useProperty: Boolean =
        firstName.isNotBlank() // parameters in the primary constructor are member properties

    /**
     * it's bit annoying here that you have to call the primary constructor always when
     * the primary constructor has arguments
     */
    constructor(firstName: String, lastName: String) : this(firstName)

    constructor(firstName: String, lastName: String, middleName: String) : this(firstName)
}

/**
 * The following code block is interesting, that's because when the primary constructor has no arguments
 * and has a init block implemented then if we invoke a second constructor we would be implicitly invoking
 * the init block of the primary constructor
 *
 * NOTE : this will be triggered first and then the body of the secondary constructor
 */

class Constructors {
    init {
        println("Init block")
    }

    constructor(i: Int) {
        println("Constructor $i")
    }
}

/**
 * Creating instances of classes
 * - no 'new' keyword! yeah !!! (or is it ?? I kind a miss it :( )
 */

val person = Person("John Doe") // it's just like a function, bit weired though...

/**
 * Inheritance
 */

class NoParent //not really true cause everyone has a parent. This implicitly inherits from 'Any'

abstract class Animal(brain: String, heart: String) //  abstract classes are open by default

open class Bird(wings: String, brain: String, heart: String) :
    Animal(brain, heart) // open is specified here because to allow extendability

class Eagle(isHawk: Boolean, wings: String, brain: String, heart: String) : Bird(wings, brain, heart)

/**
 * Overriding Methods
 *
 * - Kotlin requires explicit modifiers for overridable members (we call them open) and for overrides(
 *   First classes and now functions as well !???)
 *
 * - The open modifier has no effect when added on members of a final class (i.e.. a class with no open modifier).
 *
 * - A member marked override is itself open, i.e. it may be overridden in subclasses.
 *   If you want to prohibit re-overriding, use final (ok now this is interesting, it actually means you inherited
 *   a function from your parent but you don't want to give it to your children(something like type 2 diabetes :P ),
 *   just simply make it final)
 *
 * - an open function can not be private (Obviously !!!)
 *
 * Overriding properties
 *
 * - Each declared property can be overridden by a property with an initializer or by a property with a getter method.
 * - You can also override a val property with a var property, but not vice versa
 *
 */

open class Base(name: String) {
    open val familyMoney: Int = 1000000000 // damn rich parent !

    open val familyCar: String =
        "BMW 720i" // children can override the car but not the outsiders as it's a read only property

    val diseases = arrayOf( // bad properties which the parent doesn't want the children to have are not made open
        "diabetes",
        "cancer",
        "cholesterol",
        "parkinson"
    )

    open fun playingThePiano() {} // only members with 'open' modifier can be overridden

    open fun drinking(brand: String) {
        if (brand == "whiskey") {
            println("Enjoy !!!!")
        }
    }

    fun nonOverridableFunction() {}
}

open class Derived : Base {
    constructor(str1: String) : super(str1)

    constructor(
        str1: String,
        str2: String
    ) : super(str1) // NOTE: some super has to be always invoked as it can not be invoked form the header

    final override val familyMoney: Int // inherited family money will not go the next generation as it's made final
        get() {
            return doGambling(super.familyMoney) // all money spent on gambling :(
        }

    override var familyCar: String = // NOTE original property was a val but it's converted to a 'var' now
        super.familyCar
        get() {
            return "$field spoiler added"
        }
        set(value) {
            if (value.contains("BMW")) {
                println("I like it !!!!")
                field = value
            }
        }

    override fun playingThePiano() { // override modifier is required. If not, the compiler would complain(Thank you kotlin !!!)
        super.playingThePiano()
        println("Learnt to sing as well")
    }

    final override fun drinking(brand: String) { // Derived class does not wants it children to inherit bad functions, so make it final
        if (dayOfTheWeek() == "Saturday") {
            super.drinking(brand)
        }
    }

    fun doGambling(money: Int): Int {
        println("All in !!")
        return 100 //  lost almost for gambling :(
    }

    fun dayOfTheWeek(): String = "Saturday"
}

class SecondDerived(name: String) : Derived(name) {

}

/**
 * Inner classes
 */

open class Foo {
    open fun f() {
        println("Foo.f()")
    }

    open val x: Int get() = 1
}

class Bar : Foo() {
    override fun f() {}
    override val x: Int get() = 0

    inner class Baz {
        fun g() {
            super@Bar.f() // Calls Foo's implementation of f()
            println(super@Bar.x) // Uses Foo's implementation of x's getter
        }
    }
}

/**
 * Overriding rules
 */

open class A {
    open fun f() {
        print("A")
    }

    fun a() {
        print("a")
    }
}

interface B {
    fun f() {
        print("B")
    } // interface members are 'open' by default

    fun b() {
        print("b")
    }
}

class C : A(), B {
    override fun f() {
        super<B>.f()
        super<A>.f()
    }

}