package dev.yash.gisthub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.yash.gisthub.databinding.AuthActivityBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = AuthActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
