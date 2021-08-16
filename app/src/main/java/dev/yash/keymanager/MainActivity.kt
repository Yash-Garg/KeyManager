package dev.yash.keymanager

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import app.yash.keymanager.databinding.MainActivityBinding
import dev.yash.keymanager.ui.AuthActivity
import dev.yash.keymanager.utils.SharedPrefs

class MainActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = SharedPrefs.getEncryptedSharedPreferences(this)
        val token = prefs.getString("ACCESS_TOKEN", null)
        if (token == null) {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, token, Toast.LENGTH_SHORT).show()
        }
    }
}
