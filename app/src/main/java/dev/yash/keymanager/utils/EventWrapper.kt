package dev.yash.keymanager.utils

import androidx.lifecycle.Observer

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event. Reference ->
 * https://stackoverflow.com/a/62113974/9739475
 */
open class Event<out T>(private val content: T) {
    private var hasBeenHandled = false

    /** Returns the content and prevents its use again. */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}

class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let { value -> onEventUnhandledContent(value) }
    }
}
