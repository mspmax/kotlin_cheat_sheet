package coroutine

import kotlinx.coroutines.*

/**
 * This comes in handy when using coroutines in UI applications eg: In Android, we would want to stop any live
 * async operation when the Fragment/Activity goes to onStop()/onDestroy() lifecycle event
 */

fun main() {
    //cancelThenJoin()

    //cancelAndJoin()

    //cancelCooperatively()

    //coroutineFinally()
}

private fun sampleJob(coroutineScope: CoroutineScope): Job = coroutineScope.launch {
    repeat(1000) {
        println("job: I'm sleeping $it ")
        delay(500L)
    }
}

fun cancelThenJoin() = runBlocking {
    val job = sampleJob(this)

    delay(1300L)
    println("main: I'm tired of waiting!")
    job.cancel() // cancels the job
    job.join() // waits for job's completion
    println("main: Now I can quit.")
}

/**
 * Thanks to awesome kotlin devs we have an extension function to join
 * these b oth functions as most of the time we will be using them together
 */
fun cancelAndJoin() = runBlocking {
    val job = sampleJob(this)

    delay(1300L)
    println("main: I'm tired of waiting!")
    job.cancelAndJoin()
    println("main: Now I can quit.")
}

/**
 * All the suspending functions in kotlinx.coroutines are cancellable
 * (but, there's always a but !)
 * If a coroutine is working in a computation and does not check for cancellation, then it cannot be cancelled
 *
 * NOTE: It's important to understand that while loop is identified as a computational operation, that's the
 * only difference here.
 */
fun cancelCooperatively() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (i < 5) { // computation loop, just wastes CPU
            // print a message twice a second
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job: I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
}

/**
 * This is really interesting, basically when the job is cancelled this can be used to clear resources etc
 *
 * NOTE: This is happening because Cancellable suspending functions throw CancellationException on cancellation
 * which can be handled in the usual way. No Magic here !!!!
 */
fun coroutineFinally() = runBlocking {
    val job = launch {
        try {
            repeat(1000) { i ->
                println("job: I'm sleeping $i ...")
                delay(500L)
            }
        } finally {
            println("job: I'm running finally")
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
}

/**
 * Throws TimeoutCancellationException as we are using withTimeout() here
 */
fun coroutineTimeOut() = runBlocking {
    withTimeout(1300L) {
        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
    }
}