package dev.yash.keymanager.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.yash.keymanager.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
