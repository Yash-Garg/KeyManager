package dev.yash.keymanager.models

import androidx.annotation.Keep

@Keep
data class User(
    val login: String,
    val id: Int,
)
