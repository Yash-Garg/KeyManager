package dev.yash.keymanager.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
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
    @Json(name = "expires_at") val expiresAt: String? = null
) : Parcelable

@Keep
@Parcelize
data class Email(
    val email: String,
    val verified: Boolean
) : Parcelable

@Keep
data class GpgModel(
    @Json(name = "armored_public_key") val armoredPublicKey: String
)
