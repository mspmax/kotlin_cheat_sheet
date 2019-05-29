package classesandobjects

/**
 * Sealed classes
 * - used for representing restricted class hierarchies
 * - an extension of enum classes(Enums in steroids !)
 * - each enum constant exists only as a single instance(singleton), whereas a subclass of a sealed class
 *   can have multiple instances which can contain state
 *
 * - NOTE: can have subclasses, but all of them must be declared in the same file
 * - abstract by default, can have abstract members
 * - constructors are private by default, NO non private constructors !!!
 * - becomes handy with 'when expression', if you verify all the cases you don't need to add an else(Yes !!!!!)
 *   and smart casting is just awesome !
 */

fun main() {

}

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