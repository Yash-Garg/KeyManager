package dev.yash.keymanager.data.utils

import dev.yash.keymanager.data.BuildConfig

object Secrets {
    const val CLIENT_SECRET = BuildConfig.CLIENT_SECRET
    const val CLIENT_ID = BuildConfig.CLIENT_ID
    const val OAUTH_SCOPES =
        "admin:public_key admin:ssh_signing_key admin:gpg_key read:public_key write:public_key read:ssh_signing_key write:ssh_signing_key"
    const val REDIRECT_URI = "dev.yash.keymanager://oauth2/callback"
}
