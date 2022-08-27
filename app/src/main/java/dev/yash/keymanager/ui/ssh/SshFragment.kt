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
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.yash.keymanager.R
import dev.yash.keymanager.adapters.SshAdapter
import dev.yash.keymanager.databinding.SshFragmentBinding
import dev.yash.keymanager.utils.EventObserver
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SshFragment : Fragment() {
    private var _binding: SshFragmentBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel: SshViewModel by viewModels()

    @Inject lateinit var sshAdapter: SshAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SshFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressBar = binding.loadingIndicator
        val recyclerView = binding.sshList
        val swipeRefreshLayout = binding.sshSwiperefresh

        swipeRefreshLayout.setOnRefreshListener {
            sshAdapter.refresh()
            swipeRefreshLayout.isRefreshing = false
        }

        parentFragmentManager.setFragmentResultListener("new_ssh_key", viewLifecycleOwner) {
            _,
            bundle ->
            val data = bundle.getStringArrayList("ssh_key")
            if (!data.isNullOrEmpty()) {
                viewModel.postSshKey(data[0], data[1])
            }
        }

        viewModel.keyPosted.observe(
            viewLifecycleOwner,
            EventObserver { result ->
                if (result == "true") {
                    Snackbar.make(
                            requireParentFragment().requireView().findViewById(R.id.add_key),
                            "Key Added Successfully",
                            Snackbar.LENGTH_LONG
                        )
                        .show()
                    lifecycleScope.launch {
                        delay(1000)
                        sshAdapter.refresh()
                    }
                } else {
                    Snackbar.make(
                            requireParentFragment().requireView().findViewById(R.id.add_key),
                            result,
                            Snackbar.LENGTH_LONG
                        )
                        .show()
                }
            }
        )

        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                viewModel.getSshKeys().collectLatest { pagingData ->
                    recyclerView.adapter = sshAdapter
                    sshAdapter.submitData(pagingData)
                }
            }

            launch {
                sshAdapter.loadStateFlow.collectLatest { loadStates ->
                    progressBar.isVisible = loadStates.refresh is LoadState.Loading
                    recyclerView.isVisible =
                        loadStates.refresh is LoadState.NotLoading && sshAdapter.itemCount > 1
                    binding.emptyView.root.isVisible =
                        loadStates.refresh is LoadState.NotLoading &&
                            sshAdapter.itemCount < 1 &&
                            loadStates.refresh !is LoadState.Error
                    binding.errorView.root.isVisible = loadStates.refresh is LoadState.Error
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.sshList.adapter = null
        _binding = null
    }
}
