package com.example.mychatlibrary.actioncables

import java.net.URI

object ActionCable {
    /**
     * Create an actioncable consumer.
     */
    fun createConsumer(
            uri: URI,
            options: Consumer.Options = Consumer.Options()
    ) = Consumer(uri, options)
}
