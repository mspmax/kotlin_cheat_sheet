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
    setScreenState(ScreenState.Data(""))
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
 *
 * - restricted class hierarchies (restricted means more control)
 * - an extension of enum classes(Enums on steroids !)
 * - each enum constant exists only as a single instance(singleton), whereas a subclass of a sealed class
 *   can have multiple instances which can contain state
 *
 * - NOTE: can have subclasses, but all of them must be declared in the same file
 * - abstract by default, can have abstract members
 * - constructors are private by default, NO non private constructors !!!
 * - becomes handy with 'when expression', if you verify all the cases you don't need to add an else(Yes !!!!!)
 *   and smart casting is just awesome !
 *
 * - NOTE: Can inherit from a class which has a no argument constructor(not any other constructor!!)
 */


abstract class TestInheritance {
    open fun testing(): String {
        return ""
    }

    abstract fun testingAbstract()
}

interface TestInterface {
    fun testImpl()
}

sealed class ScreenState : TestInheritance(), TestInterface {
    object Error :
        ScreenState() // i'm using object here cause if a subclass doesn’t keep state, it can just be an object.

    object Loading : ScreenState()
    data class Data(val someData: String) : ScreenState() // an example of data class extending other classes

    override fun testingAbstract() {
        println("this is from the super class")
    }

    override fun testImpl() {
        println("this is from the interface")
    }
}

fun setScreenState(screenState: ScreenState) {
    when (screenState) {
        ScreenState.Error -> { //you can get rid of the 'is' keyword, Here you can just compare the object, as there’s
            // only one instance, you don’t need to check the type of object

            /* set error state in the view */
        }
        ScreenState.Loading -> { /* set loading state in the view */
        }
        is ScreenState.Data -> { // smart cast in action!
            /* hide loading or error states in the view and display data*/
            // update the UI here
            //sometextView.text = screenState.someData
        }
    }
}

/**
 * Another example stolen from Antonia leiva(https://antonioleiva.com/sealed-classes-kotlin/)
 *
 * - quote : Functional programming relies heavily on the idea that for a given function,
 *           same parameters will return the same result. Any state that is modified may break this assumption
 * - quote_2 : operations that have no state can be objects, because we don’t need different instances.
 */

sealed class Operation {
    class Add(val value: Int) : Operation()
    class Substract(val value: Int) : Operation()
    class Multiply(val value: Int) : Operation()
    class Divide(val value: Int) : Operation()
    object Increment : Operation()
    object Decrement : Operation()
}

fun execute(x: Int, op: Operation) = when (op) {
    is Operation.Add -> x + op.value
    is Operation.Substract -> x - op.value
    is Operation.Multiply -> x * op.value
    is Operation.Divide -> x / op.value
    Operation.Increment -> x + 1
    Operation.Decrement -> x - 1
}

/**
 * Bonus example based on https://codeascraft.com/2018/04/12/sealed-classes-opened-my-mind/
 *
 * NOTE: This is to understand the power of sealed class and it's optional to read beyond here
 */

// complicated tree structure

/*sealed class SocialSignIn {
    sealed class SignIn : SocialSignIn() {
        object Success : SignIn()
        sealed class TwoFactor : SignIn() {
            object Success : TwoFactor()
            object RetryTwoFactor : TwoFactor()
            object Fail : TwoFactor()
            object RetrySocial : TwoFactor()
        }

        object Fail : SignIn()
        object RetrySocial : SignIn()
    }

    sealed class Link : SocialSignIn() {
        object Success : Link()
        object Fail : Link()
        sealed class TwoFactor : Link() {
            object Success : TwoFactor()
            object RetryTwoFactor : TwoFactor()
            object Fail : TwoFactor()
            object RetrySocial : TwoFactor()
        }

        object RetrySocial : Link()
    }

    sealed class Register : SocialSignIn() {
        object Success : Register()
        object Fail : Register()
        object RetrySocial : Register()
    }

    sealed class Error : SocialSignIn()
}*/

// better way
sealed class SocialSignIn {
    sealed class Error : SocialSignIn()
}

sealed class SignIn : SocialSignIn() {
    object Success : SignIn()
    object Fail : SignIn()
    object RetrySocial : SignIn()
}

sealed class TwoFactor : SignIn() {
    object Success : TwoFactor()
    object RetryTwoFactor : TwoFactor()
    object Fail : TwoFactor()
    object RetrySocial : TwoFactor()
}

sealed class Link : SocialSignIn() {
    object Success : Link()
    object Fail : Link()

    sealed class TwoFactor : Link() {
        object Success : TwoFactor()
        object RetryTwoFactor : TwoFactor()
        object Fail : TwoFactor()
        object RetrySocial : TwoFactor()
    }

    object RetrySocial : Link()
}

sealed class Register : SocialSignIn() {
    object Success : Register()
    object Fail : Register()
    object RetrySocial : Register()
}

// with the above structure we can avoid SocialSignIn.SignIn.TwoFactor.Success kind of chaining
fun signIn(signInResult: TwoFactor): Boolean = when (signInResult) {
    TwoFactor.Success -> TODO()
    TwoFactor.RetryTwoFactor -> TODO()
    TwoFactor.Fail -> TODO()
    TwoFactor.RetrySocial -> TODO()
}

