package dev.yash.keymanager.data.models

import androidx.annotation.Keep

sealed interface Key

sealed interface KeyModel

@Keep data class KeyActionEvent(val type: KeyType, val event: KeyEvent, val message: String)

enum class KeyEvent {
    ADDED,
    FAILED
}
