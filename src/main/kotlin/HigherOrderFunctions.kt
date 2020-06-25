fun main() {

    hOrderNoParam("STREAM") { updateUi() }

    hOrderParam("STREAM") {
        if (it.isNotEmpty().and(it.endsWith("M"))) {
            updateUi()
        }
    }

    hOrderMultiParam("STREAM") { str, mediaType ->
        when {
            str.isNotEmpty() && str == mediaType -> updateUi()
            else -> println("provided type validation failed and UI NOT updated")
        }
    }

    hOrderMultiParamWithReturn("DOWNLOAD") { str, mediaType ->
        when {
            str.isNotEmpty() && str == mediaType -> true
            else -> false
        }
    }

    // real world
    fetchData({ println(it) }, { println(it.message) })
}

// lambda expressions
val testEmpty: () -> Unit = { println("testing empty lambada expression") }

val check: (Int) -> Unit = { a -> if (a > 2) println("value larger than 2") }

val square: (Int) -> Int = { a -> a * a }

val multiply: (Int, Int) -> Int = { a, b -> a * b }


// higher order functions
fun hOrderNoParam(str: String, informUi: () -> Unit) {
    if (str.isNotEmpty()) {
        informUi()
    }
}

fun hOrderParam(str: String, validateAndInformUi: (String) -> Unit) {
    validateAndInformUi(str)
    println("classesandobjects.getStr validation completed")
}

fun hOrderMultiParam(str: String, validateAndInformUi: (String, String) -> Unit) {
    validateAndInformUi(str, "DOWNLOAD")
    println("classesandobjects.getStr validation completed for DOWNLOAD types")
}

fun hOrderMultiParamWithReturn(str: String, validateAndInformUi: (String, String) -> Boolean) {
    when {
        validateAndInformUi(str, "DOWNLOAD") -> updateUi()
        else -> println("classesandobjects.getStr validation not successful and UI NOT updated")
    }
}

fun updateUi() {
    println("UI updated successfully")
}

// real world scenario
fun fetchData(success: (String) -> Unit, error: (Throwable) -> Unit) {
    val response = fetchResponseFromServer()

    when {
        response.httpCode == "200" -> success("Kotlin is awesome")
        else -> error(Throwable("server is shit"))
    }
}

fun fetchResponseFromServer(): Response {
    // long running process in a background thread
    return Response("200")
}

data class Response(val httpCode: String)