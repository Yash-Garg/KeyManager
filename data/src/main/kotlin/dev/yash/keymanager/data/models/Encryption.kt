package dev.yash.keymanager.data.models

enum class Encryption {
    DSA,
    ECDSA,
    ED25519,
    ECDSA_SK,
    ED25519_SK;

    companion object {
        fun enumFor(key: String): Encryption {
            return when {
                key.contains("ssh-dsa") -> DSA
                key.contains("ssh-ecdsa") -> ECDSA
                key.contains("ssh-ed25519") -> ED25519
                key.contains("ssh-ecdsa-sk") -> ECDSA_SK
                key.contains("ssh-ed25519-sk") -> ED25519_SK
                else -> throw IllegalArgumentException()
            }
        }
    }

    override fun toString(): String {
        return when (this) {
            ECDSA_SK -> "ECDSA-SK"
            ED25519_SK -> "ED25519-SK"
            else -> super.toString()
        }
    }
}
