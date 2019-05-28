package classesandobjects

/**
 * Data class
 *
 * Only responsibility is to act as a state container (the best thing about kotlin for me personally!
 * specially when you're working with an enterprise API with a lot of data models.This could remove loads of
 * boilerplate which are annoying and hard to maintain)
 *
 * What happens under the hood, compiler does most of the stuff for us implicitly
 *  - equals()/hashCode() pair
 *  - toString() for this particular example, in the form of "User(name=John, age=42)"
 *  - copy()
 *  - componentN() functions
 *
 * Few requirements,
 *  - primary constructor should always have at lest one param (make sense, or else why would you have a data class)
 *  - All primary constructor params need to be marked as val or var
 *  - cannot be abstract, open, sealed or inner (nice and simple)
 *  - Providing explicit implementations for the componentN() and copy() functions is not allowed.
 *
 * Try to avoid Pair<> and Triple<> as much as possible as named data classes makes more sense on code readability
 *
 * data classes can not be inherited, final by default and can not be opened
 *
 * TL;DR - Word of advice, please try to preserve immutability in data classes and keep it simple without any logic.
 * Try to always respect clean architecture concepts. In my opinion these can be seen as entities in clean acrh.
 * https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html
 *
 * Use always copy function when you want to change the state of a data class and do this in a different layer
 */

fun main() {

}

data class User(val name: String, val age: Int)

/**
 * Compiler only uses the properties defined inside the primary constructor. If you want to omit something
 * declare it in the class body instead(but why would you omit it ??? )
 *
 * Only the property name will be used inside the toString(), equals(), hashCode(), and copy() implementations,
 * and there will only be one component function component1()
 *
 * eg: if two users have same name here but different age then it will be treated as equal(TBH I would not complicate
 * things like data classes, it has a purpose and a responsibility so lets stick to it without doing mix and match )
 *
 * NOTE: I would prefer not to do this as per the same reasons mentioned in the introduction
 */
data class User2(val name: String) {
    var age: Int = 0 // age is not included in the auto generated functions eg: equals()
}

class TestingCopyFunction {
    val userX = User("John Doe", 33)
    val userY = userX.copy(age = 34)// NOTE: copy() retains the original object and returns an immutable copy of it

    val person1 = User2("John")
    val person2 = User2("John")

    init {
        person1.age = 55
        person2.age = 65

        if (person1 == person2) {
            println("person 1 and 2 are the same")
        }
    }
}


