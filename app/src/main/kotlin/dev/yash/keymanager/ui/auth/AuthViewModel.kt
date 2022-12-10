package dev.yash.keymanager.ui.auth

import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.yash.keymanager.data.utils.AuthConfig
import dev.yash.keymanager.data.utils.Secrets
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
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
    private val _authStatus = MutableSharedFlow<AuthStatus>()
    val authStatus = _authStatus.asSharedFlow()

    init {
        viewModelScope.launch { checkAuthentication() }
    }

    private suspend fun checkAuthentication() {
        val token = preferences.getString(AuthConfig.TOKEN_KEY, null)
        if (token != null) {
            _authStatus.emit(AuthStatus.AUTHENTICATED)
        } else {
            _authStatus.emit(AuthStatus.UNAUTHENTICATED)
        }
    }

    fun requestAccessToken(authIntent: Intent) {
        val resp = AuthorizationResponse.fromIntent(authIntent)
        val clientAuth = ClientSecretBasic(Secrets.CLIENT_SECRET)

        if (resp != null) {
            authService.performTokenRequest(resp.createTokenExchangeRequest(), clientAuth) {
                response,
                _ ->
                if (response != null) {
                    requireNotNull(response.accessToken).let {
                        preferences.edit().putString(AuthConfig.TOKEN_KEY, it).apply()
                        viewModelScope.launch { _authStatus.emit(AuthStatus.AUTHENTICATED) }
                    }
                }
            }
        }
    }
}
