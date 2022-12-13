package dev.yash.keymanager.data.models

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class SshKey(
    val id: Long,
    val key: String,
    val url: String? = null,
    val title: String,
    val verified: Boolean? = null,
    @Json(name = "created_at") val createdAt: String,
    @Json(name = "read_only") val readOnly: Boolean? = null
) : Key

@Keep data class SshModel(val title: String, val key: String) : KeyModel
