package dev.yash.keymanager.ui

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import app.yash.keymanager.R
import app.yash.keymanager.databinding.AuthFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.yash.keymanager.utils.AuthConfig
import dev.yash.keymanager.utils.Secrets
import dev.yash.keymanager.utils.SharedPrefs
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.ClientSecretBasic

@AndroidEntryPoint
class AuthFragment : Fragment(R.layout.auth_fragment) {
    private lateinit var authService: AuthorizationService
    private lateinit var binding: AuthFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AuthFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginButton = binding.signinButton
        authService = AuthorizationService(requireActivity().applicationContext)

        val getAuthCodeFromResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    result.data?.let { getAccessToken(it) }
                }
            }

        loginButton.setOnClickListener {
            val authIntent = authService.getAuthorizationRequestIntent(AuthConfig.authRequest)
            getAuthCodeFromResult.launch(authIntent)
        }
    }

    private fun getAccessToken(authIntent: Intent) {
        val resp = AuthorizationResponse.fromIntent(authIntent)
        val clientAuth = ClientSecretBasic(Secrets.CLIENT_SECRET)

        if (resp != null) authService.performTokenRequest(
            resp.createTokenExchangeRequest(),
            clientAuth,
        ) { response, exception ->
            if (response != null) {
                response.accessToken?.let {
                    val prefs = SharedPrefs.getEncryptedSharedPreferences(requireContext())
                    prefs.edit().putString("ACCESS_TOKEN", it).apply()
                }
            } else {
                Log.e("Error", exception.toString())
            }
        }
    }
}
