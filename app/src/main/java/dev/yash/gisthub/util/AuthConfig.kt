package dev.yash.gisthub.util

import android.net.Uri
import net.openid.appauth.AuthorizationServiceConfiguration

class AuthConfig {
    private val serviceConfiguration = AuthorizationServiceConfiguration(
        Uri.parse("https://github.com/login/oauth/authorize"),
        Uri.parse("https://github.com/login/oauth/access_token")
    )
}
