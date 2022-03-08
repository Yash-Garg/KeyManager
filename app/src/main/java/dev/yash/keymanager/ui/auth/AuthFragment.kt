package dev.yash.keymanager.ui.auth

import android.app.Activity.RESULT_OK
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import app.yash.keymanager.R
import app.yash.keymanager.databinding.AuthFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.yash.keymanager.utils.AuthConfig
import javax.inject.Inject
import net.openid.appauth.AuthorizationService
import timber.log.Timber

@AndroidEntryPoint
class AuthFragment : Fragment(R.layout.auth_fragment) {
    private var _binding: AuthFragmentBinding? = null
    private val binding
        get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()

    @Inject lateinit var preferences: SharedPreferences

    @Inject lateinit var authService: AuthorizationService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AuthFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

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

    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
