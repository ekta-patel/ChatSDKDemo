package com.example.mychatlibrary.actioncables

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import kotlin.coroutines.CoroutineContext

class EventsHandler : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    private val actor = actor<suspend () -> Unit> {
        for (msg in channel) {
            msg.invoke()
        }
    }

    fun handle(operation: suspend () -> Unit) = launch {
        send(operation)
    }

    fun handleWithDelay(operation: suspend () -> Unit, duration: Long) = launch {
        delay(duration)
        send(operation)
    }

    private suspend fun send(operation: suspend () -> Unit) {
        if (!actor.isClosedForSend) {
            actor.send(operation)
        }
    }

    // TODO: actor.close() disabled due to possible ClosedSendChannelException. Issue should be fixed in the next version.
    fun stop() {
        //   actor.close()
        coroutineContext.cancel()
    }
}
