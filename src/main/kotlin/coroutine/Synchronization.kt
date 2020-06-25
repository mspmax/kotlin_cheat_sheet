package coroutine

import kotlinx.coroutines.*

/**
 * --Problem--
 * launching 1000 coroutines on 4 threads, and each of them, increment the global value
 * sharedCounter 1000 times, so the final value of the sharedCounter should be 1000000
 *
 * In order to increment the sharedCounter, each thread is trying to do following to internally
 * increment the sharedCounter value:
 * 1. Get its current value,
 * 2. Store it in a temp variable, and increment the temp variable by 1,
 * 3. Save the temp variable to sharedCounter.
 *
 * But above will make problems when you're in multi threaded world
 *
 * eg: a thread gets past the second level, but before storing it, other threads increment
 * and save the sharedCounter value, and when the first thread jumps in for its third step,
 * it saves an older version of the sharedCounter. This is why the final value is not what we
 * expect in the method [threadUnSafe] as we haven't use synchronization.
 *
 * in simple words,
 * --why thread safety is important--
 * Imagine there’s one room (the counter) and many people (threads) want to use it but only one person is allowed at a
 * time. This room has a door that when the room is occupied, is closed. What happens in this scenario is, when one
 * person is inside the room, other people can also open the door, come in and use the room.
 *
 * --how to fix it--
 * We need a lock on the door that has only one key, that people need to use to open the door and lock it. So when one
 * person (thread) goes in, they can use the key to lock the door, and since no one else has the key, they cannot go in
 * until the key is returned to them by the original person
 */
@ObsoleteCoroutinesApi
fun main() {
    threadUnSafe()
}

var sharedCounter = 0
    private set

@Synchronized
fun updateCounter() {
    sharedCounter++
}

@ObsoleteCoroutinesApi
fun threadUnSafe() = runBlocking {
    var sharedCounter = 0
    val scope =
        CoroutineScope(newFixedThreadPoolContext(4, "synchronizationPool")) // We want our code to run on 4 threads
    scope.launch {
        val coroutines = 1.rangeTo(1000).map {
            //create 1000 coroutines (light-weight threads).
            launch {
                for (i in 1..1000) { // and in each of them, increment the sharedCounter 1000 times.
                    sharedCounter++
                }
            }
        }

        coroutines.forEach { coroutine ->
            coroutine.join() // wait for all coroutines to finish their jobs.
        }
    }.join()

    println("The number of shared counter should be 1000000, but actually is $sharedCounter")
}

@ObsoleteCoroutinesApi
fun threadSynchronized() = runBlocking {
    val scope =
        CoroutineScope(newFixedThreadPoolContext(4, "synchronizationPool")) // We want our code to run on 4 threads
    scope.launch {
        val coroutines = 1.rangeTo(1000).map {
            //create 1000 coroutines (light-weight threads).
            launch {
                for (i in 1..1000) { // and in each of them, increment the sharedCounter 1000 times.
                    updateCounter() // call the newly created function that is now synchronized
                }
            }
        }

        coroutines.forEach { coroutine ->
            coroutine.join() // wait for all coroutines to finish their jobs.
        }
    }.join()

    println("The number of shared counter is $sharedCounter")
}