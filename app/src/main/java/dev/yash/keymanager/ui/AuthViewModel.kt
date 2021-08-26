package dev.yash.keymanager.ui

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.yash.keymanager.utils.Secrets
import dev.yash.keymanager.utils.SharedPrefs
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.ClientSecretBasic
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() :
    ViewModel() {
    fun getAccessToken(authService: AuthorizationService, authIntent: Intent, ctx: Context) {
        val resp = AuthorizationResponse.fromIntent(authIntent)
        val clientAuth = ClientSecretBasic(Secrets.CLIENT_SECRET)

        if (resp != null) authService.performTokenRequest(
            resp.createTokenExchangeRequest(),
            clientAuth,
        ) { response, exception ->
            if (response != null) {
                response.accessToken?.let {
                    val prefs = SharedPrefs.getEncryptedSharedPreferences(ctx)
                    prefs.edit().putString("ACCESS_TOKEN", it).apply()
                }
            } else {
                Log.e("Error", exception.toString())
            }
        }
    }
}
