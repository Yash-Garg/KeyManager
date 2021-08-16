package dev.yash.keymanager

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import dev.yash.keymanager.ui.AuthActivity
import dev.yash.keymanager.ui.HomeActivity
import dev.yash.keymanager.utils.SharedPrefs

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prefs = SharedPrefs.getEncryptedSharedPreferences(this)
        val token = prefs.getString("ACCESS_TOKEN", null)
        if (token == null) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}
