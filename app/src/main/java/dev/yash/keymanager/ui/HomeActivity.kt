package dev.yash.keymanager.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import app.yash.keymanager.databinding.HomeActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.yash.keymanager.adapters.SshAdapter
import dev.yash.keymanager.utils.SharedPrefs
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val homeViewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var sshAdapter: SshAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.background = null

        val accessToken = SharedPrefs.getAccessToken(this)
        val progressBar = binding.loadingIndicator
        val recyclerView = binding.sshList

        lifecycleScope.launch {
            homeViewModel.getKeysSSH(accessToken!!).collectLatest { pagingData ->
                recyclerView.adapter = sshAdapter
                sshAdapter.submitData(pagingData)
            }
        }

        lifecycleScope.launch {
            sshAdapter.loadStateFlow.collectLatest { loadStates ->
                progressBar.isVisible = loadStates.refresh is LoadState.Loading
                recyclerView.isVisible = loadStates.refresh is LoadState.NotLoading
            }
        }
    }
}
