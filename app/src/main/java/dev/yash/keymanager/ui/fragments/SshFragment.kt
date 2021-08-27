package dev.yash.keymanager.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import app.yash.keymanager.databinding.SshFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.yash.keymanager.adapters.SshAdapter
import dev.yash.keymanager.ui.viewmodels.SshViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

@AndroidEntryPoint
class SshFragment : Fragment() {
    private val viewModel: SshViewModel by viewModels()
    private lateinit var binding: SshFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SshFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // val progressBar = binding.loadingIndicator
        val recyclerView = binding.sshList

        lifecycleScope.launch {
            viewModel.sshKeys.collectLatest {
                val response = it.awaitResponse()
                if (response.isSuccessful) {
                    recyclerView.adapter = response.body()?.let { data -> SshAdapter(data) }
                }
            }
        }
    }
}
