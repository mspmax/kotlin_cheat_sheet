import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicLong
import kotlin.concurrent.thread

/**
 * Before anything else please read this document https://kotlinlang.org/docs/tutorials/coroutines/async-programming.html
 * which gives promising insights around asynchronous programming and why choose coroutines over other solutions
 *
 * In my own words and experience this is what coroutines sounds to me,
 *  -   coroutine is a light-weight thread
 *  -   can run in parallel, wait for each other and communicate
 *  -   cheaper than threads (can create thousands of them without hindering performance. Boom !!). Threads are expensive
 *      to start and keep around
 *  -   Coroutines are run on a shared pool of thread(so threads do exist under the hood. Boom !! again).
 *      One thread can run many coroutines, so we can avoid creating too many threads
 */
fun main() {
    //coroutinesDelay()

    //coroutinesAreCheap()

    //asyncCoroutine()
}

/**
 *  -   delay() is a suspend function and suspend functions can be called from only a suspend function or a coroutine
 *  -   delay() is something like Thread.sleep(), but better. doesn't block a thread, but only suspends the coroutine itself.
 *      The thread is returned to the pool while the coroutine is waiting, and when the waiting is done,
 *      the coroutine resumes on a free thread in the pool
 */
fun coroutinesDelay() {
    println("Start")

    // Starts a coroutine does not wait until it's done. Completely asynchronous
    GlobalScope.launch {
        delay(1000)
        println("Hello")
    }

    // Starts a coroutine and waits until it's done (blocking code)
    runBlocking {
        delay(4000)
        println("Blocking")
    }

    Thread.sleep(2000) // wait for 2 seconds
    println("Stop")
}

/**
 * Running million threads
 */
fun threadsAreExpensive() {
    val c = AtomicLong()

    for (i in 1..1_000_000L) {
        thread {
            c.addAndGet(i)
        }
    }

    println(c.get())
}

/**
 * Running million coroutines
 */
fun coroutinesAreCheap() {
    val c = AtomicLong()

    for (i in 1..1_000_000L)
        GlobalScope.launch {
            c.addAndGet(i)
        }

    println(c.get())
}

/**
 * Running million coroutines and returning a value from a coroutine
 */
fun asyncCoroutine() {
    val deferred = (1..1_000_000).map {
        GlobalScope.async {
            delay(1000) // to check whether coroutines do actually runs in parallel
            it
        }
    }

    // await() can not be called outside a coroutine that why runBlocking{} is used here
    runBlocking {
        val sum = deferred.sumBy { it.await() }

        println("Sum: $sum")
    }
}

/**
 * -    coroutines can suspend without blocking a thread
 * -    when we use suspend and call the function from a coroutine, the compiler knows that
 *      it may suspend and will prepare accordingly
 * -    REPEAT : This can only be used within a suspend function or
 *      within a coroutine eg: launch{}, async{}, runBlocking{}
 */
suspend fun suspendingFunction(num: Int): Int {
    delay(1000)
    return num
}

