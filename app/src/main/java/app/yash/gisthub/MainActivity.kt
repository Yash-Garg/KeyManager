package app.yash.gisthub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.yash.gisthub.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
