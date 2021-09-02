package dev.yash.keymanager.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class SshKey(
    val id: Long,
    val key: String,
    val url: String,
    val title: String,
    val verified: Boolean,
    @Json(name = "created_at") val createdAt: String,
    @Json(name = "read_only") val readOnly: Boolean
) : Parcelable

@Keep
data class SshModel(
    val key: String
)
