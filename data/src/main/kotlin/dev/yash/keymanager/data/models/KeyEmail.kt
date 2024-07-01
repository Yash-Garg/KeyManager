package dev.yash.keymanager.data.models

import androidx.annotation.Keep

@Keep data class KeyEmail(val email: String, val verified: Boolean)
