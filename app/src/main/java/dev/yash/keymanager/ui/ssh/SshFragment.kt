package dev.yash.keymanager.ui.ssh

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import app.yash.keymanager.databinding.SshFragmentBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.yash.keymanager.adapters.SshAdapter
import dev.yash.keymanager.models.SshModel
import dev.yash.keymanager.ui.dialogs.SshNewKeyDialogFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SshFragment : Fragment() {
    private var _binding: SshFragmentBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: SshViewModel by viewModels()

    @set:Inject
    var sshAdapter: SshAdapter? = null

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
        val swipeRefreshLayout = binding.sshSwiperefresh

        swipeRefreshLayout.setOnRefreshListener {
            sshAdapter?.refresh()
            swipeRefreshLayout.isRefreshing = false
        }

        addFab.setOnClickListener {
            SshNewKeyDialogFragment.newInstance().show(childFragmentManager, null)
        }

        childFragmentManager.setFragmentResultListener(
            "new_ssh_key",
            viewLifecycleOwner
        ) { _, bundle ->
            val newSshKey = bundle.getString("ssh_key")
            if (!newSshKey.isNullOrEmpty()) {
                viewModel.postSshKey(SshModel(newSshKey))
                viewModel.keyPosted.observe(viewLifecycleOwner) { result ->
                    if (result == true) {
                        Snackbar.make(view, "Key Added Successfully", Snackbar.LENGTH_SHORT).show()
                        lifecycleScope.launch {
                            delay(1000)
                            sshAdapter?.refresh()
                        }
                    } else {
                        Snackbar.make(view, "Some error occured", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.getSshKeys().collectLatest { pagingData ->
                recyclerView.adapter = sshAdapter
                sshAdapter?.submitData(pagingData)
            }
        }

        lifecycleScope.launch {
            sshAdapter?.loadStateFlow?.collectLatest { loadStates ->
                progressBar.isVisible = loadStates.refresh is LoadState.Loading
                recyclerView.isVisible = loadStates.refresh is LoadState.NotLoading
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sshAdapter = null
        _binding = null
    }
}
