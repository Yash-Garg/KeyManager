package app.yash.gisthub

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import app.yash.gisthub.databinding.AuthActivityBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = AuthActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val authButton = binding.signinButton
        authButton.setOnClickListener {
            Toast.makeText(this, "Please wait... Signing in", Toast.LENGTH_LONG).show()
        }
    }
}
