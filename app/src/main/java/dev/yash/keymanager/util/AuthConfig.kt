package dev.yash.keymanager.util

import android.net.Uri
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ResponseTypeValues

object AuthConfig {
    private val serviceConfiguration = AuthorizationServiceConfiguration(
        Uri.parse("https://github.com/login/oauth/authorize"),
        Uri.parse("https://github.com/login/oauth/access_token")
    )

    private val authRequestBuilder = AuthorizationRequest.Builder(
        serviceConfiguration,
        Secrets.CLIENT_ID,
        ResponseTypeValues.TOKEN,
        Uri.parse(Secrets.REDIRECT_URI),
    )

    val authRequest = authRequestBuilder.setScope(Secrets.OAUTH_SCOPES).build()
}
