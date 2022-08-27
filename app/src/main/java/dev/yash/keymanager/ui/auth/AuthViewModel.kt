package dev.yash.keymanager.ui.auth

import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.yash.keymanager.utils.Secrets
import javax.inject.Inject
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.ClientSecretBasic
import timber.log.Timber

@HiltViewModel
class AuthViewModel
@Inject
constructor(
    private val preferences: SharedPreferences,
    private val authService: AuthorizationService
) : ViewModel() {
    var accessToken: MutableLiveData<String> = MutableLiveData()

    fun getAccessToken(authIntent: Intent) {
        val resp = AuthorizationResponse.fromIntent(authIntent)
        val clientAuth = ClientSecretBasic(Secrets.CLIENT_SECRET)

        if (resp != null)
            authService.performTokenRequest(
                resp.createTokenExchangeRequest(),
                clientAuth,
            ) { response, exception ->
                if (response != null) {
                    response.accessToken?.let {
                        accessToken.value = it
                        preferences.edit().putString("ACCESS_TOKEN", it).apply()
                    }
                } else Timber.e(exception)
            }
    }
}
