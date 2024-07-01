package dev.yash.keymanager.data.models

enum class KeyType(val title: String) {
    SSH("SSH "),
    SSH_SIGNING("SSH SIGNING"),
    GPG("GPG");

    companion object {
        val all = listOf(SSH, SSH_SIGNING, GPG)
    }
}
