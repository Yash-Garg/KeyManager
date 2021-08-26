package dev.yash.keymanager

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import app.yash.keymanager.R
import app.yash.keymanager.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.yash.keymanager.utils.SharedPrefs

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val token = SharedPrefs.getAccessToken(this)
        if (token == null) {
            navController.navigate(R.id.authFragment)
        } else {
            Log.d("Access Token", token)
        }
    }
}
