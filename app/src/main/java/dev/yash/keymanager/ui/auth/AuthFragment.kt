package dev.yash.keymanager.ui.auth

import android.app.Activity.RESULT_OK
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import dev.yash.keymanager.R
import dev.yash.keymanager.databinding.AuthFragmentBinding
import dev.yash.keymanager.utils.AuthConfig
import dev.yash.keymanager.utils.viewBinding
import javax.inject.Inject
import net.openid.appauth.AuthorizationService
import timber.log.Timber

@AndroidEntryPoint
class AuthFragment : Fragment(R.layout.auth_fragment) {
    private val binding by viewBinding(AuthFragmentBinding::bind)
    private val authViewModel: AuthViewModel by viewModels()

    @Inject lateinit var preferences: SharedPreferences

    @Inject lateinit var authService: AuthorizationService

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val token = preferences.getString("ACCESS_TOKEN", null)
        if (!token.isNullOrEmpty()) {
            Timber.d("Found Token - $token")
            Navigation.findNavController(view).navigate(R.id.action_authFragment_to_homeFragment)
            return
        }

        super.onViewCreated(view, savedInstanceState)

        val loginButton = binding.signinButton

        val getAuthCodeFromResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    result.data?.let { authViewModel.getAccessToken(it) }
                }
            }

        loginButton.setOnClickListener {
            val authIntent = authService.getAuthorizationRequestIntent(AuthConfig.authRequest)
            getAuthCodeFromResult.launch(authIntent)
        }

        authViewModel.accessToken.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                Navigation.findNavController(view)
                    .navigate(R.id.action_authFragment_to_homeFragment)
            }
        }
    }
}
