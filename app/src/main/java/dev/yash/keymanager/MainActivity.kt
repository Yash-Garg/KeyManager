package dev.yash.keymanager

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.yash.keymanager.ui.AuthActivity
import dev.yash.keymanager.utils.SharedPrefs

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val token = SharedPrefs.getAccessToken(this)
        if (token == null) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }
    }
}
