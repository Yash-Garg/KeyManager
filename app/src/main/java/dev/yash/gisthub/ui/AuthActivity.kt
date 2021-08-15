package dev.yash.gisthub.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import app.yash.gisthub.databinding.ActivityAuthBinding
import dev.yash.gisthub.util.AuthConfig
import net.openid.appauth.AuthorizationService

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loginButton = binding.signinButton

        loginButton.setOnClickListener {
            val authService = AuthorizationService(this)
            val authIntent = authService.getAuthorizationRequestIntent(AuthConfig.authRequest)
            getCode.launch(authIntent)
        }
    }

    private val getCode =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                Toast.makeText(this, "Success! Token Received", Toast.LENGTH_SHORT).show()
                println(result.data)
            } else {
                Toast.makeText(this, "Error Retrieving Token", Toast.LENGTH_SHORT).show()
            }
        }
}
