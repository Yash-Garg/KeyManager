package dev.yash.keymanager.models

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class GpgKey(
    val id: Long,
    @Json(name = "primary_key_id") val primaryKeyID: Long? = null,
    @Json(name = "key_id") val keyID: String,
    @Json(name = "raw_key") val rawKey: String? = null,
    @Json(name = "public_key") val publicKey: String,
    val emails: List<Email>,
    val subkeys: List<GpgKey>,
    @Json(name = "can_sign") val canSign: Boolean,
    @Json(name = "can_encrypt_comms") val canEncryptComms: Boolean,
    @Json(name = "can_encrypt_storage") val canEncryptStorage: Boolean,
    @Json(name = "can_certify") val canCertify: Boolean,
    @Json(name = "created_at") val createdAt: String,
    @Json(name = "expires_at") val expiresAt: Any? = null
)

@Keep
data class Email(
    val email: String,
    val verified: Boolean
)
