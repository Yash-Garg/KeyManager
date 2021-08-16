package dev.yash.keymanager.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.yash.keymanager.databinding.HomeActivityBinding
import dev.yash.keymanager.utils.SharedPrefs

class HomeActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = SharedPrefs.getEncryptedSharedPreferences(this)
        val token = prefs.getString("ACCESS_TOKEN", null)
        println(token)
    }
}
