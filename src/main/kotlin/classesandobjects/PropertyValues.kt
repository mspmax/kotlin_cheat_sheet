package classesandobjects

/** Based on https://kotlinlang.org/docs/reference/properties.html
 *
 * full syntax when defining a property
 *   [var|val] <propertyName>[: <PropertyType>] [= <property_initializer>]
 *[<getter>]
 *[<setter>]
 *
 * - The initializer, getter and setter are optional.
 * - Property type is optional if it can be inferred from the initializer
 *   eg: val classesandobjects.getNum = 78
 * */

fun main() {
    // playground :)
}

/**
 * Immutable properties(read only)
 *
 * - Does not allow a setter.
 * - Can have a custom getter which will be called every
 *   time we access the property(computable property).
 *
 * - You can not initialize and use a custom getter as there's no
 *   backing field for immutable properties
 *
 * val classesandobjects.getNumWithCustomGetter: Int = 69 // ERROR !!
 *    get() = 1234
 *
 */

val num: Int = 0

val numWithCustomGetter: Int
    get() = 1234

val numWithCustomGetterNoType get() = 1234 // omit the property type if it can be inferred from the getter

val numWithLazy by lazy { 100 } // only used with immutable properties

/**
 * Mutable properties
 *
 * Can have custom accessors(getters and setters),
 *  - get() - will be called every time when you access the property
 *  - set() - will be called every time when you assign a value to the property
 *
 *  A backing field will be generated for a property if it uses the default implementation
 *  of at least one of the accessors, or if a custom accessor references it through the field identifier.
 */

var str = "kotlin is awesome !" // Note: the initializer assigns the backing field directly

var strWithCustomGetter: String = "default" // var properties should always be initialized because of the backing filed
    get() = "$field custom"

var strWithCustomSetter: String = "default"
    set(value) {
        println("do anything you want as this point")
        field = value // Warning:: this is important to do if you want the assign value to be set to the 'backing field'
    }

var strWithCustomGetterAndSetter: String = "default"
    get() = "$field custom"
    set(value) {
        println("do anything you want as this point")
        field = value // Warning:: this is important to do if you want the assign value to be set to the 'backing field'
    }

var strWithPrivateDefaultSetter: String = "default"
    private set // If you need to change the visibility of an accessor(valid to bot the getters and setters)

var strWithPrivateCustomSetter: String = "default"
    private set(value) {
        println("do anything you want as this point")
        field = value // WARNING AGAIN!! value or any value should be assigned at this point
    }

/**
 * Lateinit (only used with mutable properties)
 *
 * - Why do you want this ?? well, think of dependency injection :P
 * - property can be validated before accessing eg: ::classesandobjects.getStrLateInit.isInitialized  phhheewwwww !!!
 * - The modifier can be used on var properties declared inside the body of a class, top-level properties
 *   and local variables.
 * - only when the property does not have a custom getter or setter
 * - must be non-null
 * - it must not be a primitive type, yeah so don't be dumb and try to add lateinit for Int's :P.
 */
lateinit var strLateInit: String

/**
 * Compile Time Constants
 *
 * - Top-level, or member of an object declaration or a companion object.
 * - Initialized with a value of type String or a primitive type, no Objects ???? Damn it !
 * - No custom getter, obviously no custom setter FFS it's a val after all !
 *
 */

const val KEY_COLUMN_NAME: String = "key_name"

/**
 * Using standard functions on properties
 */

val stringProperty = "first property".also(::println)// same as .also({println(it)})