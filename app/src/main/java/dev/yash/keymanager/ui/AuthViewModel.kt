package dev.yash.keymanager.ui

import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.yash.keymanager.utils.Secrets
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.ClientSecretBasic
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val preferences: SharedPreferences,
    private val authService: AuthorizationService
) : ViewModel() {

    fun getAccessToken(authIntent: Intent) {
        val resp = AuthorizationResponse.fromIntent(authIntent)
        val clientAuth = ClientSecretBasic(Secrets.CLIENT_SECRET)

        if (resp != null) authService.performTokenRequest(
            resp.createTokenExchangeRequest(),
            clientAuth,
        ) { response, exception ->
            if (response != null) {
                response.accessToken?.let {
                    preferences.edit().putString("ACCESS_TOKEN", it).apply()
                }
            } else {
                Log.e("Error", exception.toString())
            }
        }
    }
}
