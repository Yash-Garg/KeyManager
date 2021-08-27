package dev.yash.keymanager.ui.ssh

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import app.yash.keymanager.databinding.SshFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.yash.keymanager.adapters.SshAdapter
import dev.yash.keymanager.ui.dialogs.NewKeyDialogFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SshFragment : Fragment() {
    private var _binding: SshFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SshViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SshFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressBar = binding.loadingIndicator
        val recyclerView = binding.sshList
        val addFab = binding.addSsh
        progressBar.isVisible = true

        addFab.setOnClickListener {
            NewKeyDialogFragment.newInstance().show(childFragmentManager, null)
        }

        lifecycleScope.launch {
            viewModel.sshKeys.collectLatest { keys ->
                progressBar.isVisible = false
                recyclerView.adapter = SshAdapter(keys)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
