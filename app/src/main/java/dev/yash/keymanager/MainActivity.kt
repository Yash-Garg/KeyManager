package dev.yash.keymanager

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import app.yash.keymanager.databinding.MainActivityBinding
import dev.yash.keymanager.ui.AuthActivity
import dev.yash.keymanager.util.SharedPrefs

class MainActivity : AppCompatActivity() {
    private val prefs = SharedPrefs.getEncryptedSharedPreferences(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = prefs.getString("ACCESS_TOKEN", null)
        if (token == null) {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, token, Toast.LENGTH_SHORT).show()
        }
    }
}
