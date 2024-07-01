package dev.yash.keymanager.ui.auth

import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.yash.keymanager.data.utils.AuthConfig
import dev.yash.keymanager.data.utils.Secrets
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.ClientSecretBasic

@HiltViewModel
class AuthViewModel
@Inject
constructor(
    private val preferences: SharedPreferences,
    private val authService: AuthorizationService
) : ViewModel() {
    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState.asStateFlow()

    init {
        checkAuthentication()
    }

    private fun checkAuthentication() {
        val token = preferences.getString(AuthConfig.TOKEN_KEY, null)
        if (token != null) {
            _authState.update { it.copy(isAuthenticated = true) }
        }
    }

    fun getAuthReqIntent(): Intent =
        authService.getAuthorizationRequestIntent(AuthConfig.authRequest)

    fun requestAccessToken(authIntent: Intent) {
        val resp = AuthorizationResponse.fromIntent(authIntent)
        val clientAuth = ClientSecretBasic(Secrets.CLIENT_SECRET)

        if (resp != null) {
            authService.performTokenRequest(resp.createTokenExchangeRequest(), clientAuth) {
                response,
                _ ->
                if (response != null) {
                    saveToken(requireNotNull(response.accessToken))
                }
            }
        }
    }

    fun saveToken(token: String) {
        preferences.edit().putString(AuthConfig.TOKEN_KEY, token).apply()
        _authState.update { it.copy(isAuthenticated = true) }
    }
}
