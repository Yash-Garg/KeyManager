package dev.yash.keymanager.ui.gpg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import app.yash.keymanager.R
import app.yash.keymanager.databinding.GpgFragmentBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.yash.keymanager.adapters.GpgAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GpgFragment : Fragment() {
    private var _binding: GpgFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GpgViewModel by viewModels()

    @Inject
    lateinit var gpgAdapter: GpgAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GpgFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressBar = binding.loadingIndicator
        val recyclerView = binding.gpgList
        val swipeRefreshLayout = binding.gpgSwiperefresh

        swipeRefreshLayout.setOnRefreshListener {
            gpgAdapter.refresh()
            swipeRefreshLayout.isRefreshing = false
        }

        parentFragmentManager.setFragmentResultListener(
            "new_gpg_key",
            viewLifecycleOwner
        ) { _, bundle ->
            val newGpgKey = bundle.getString("gpg_key")
            if (!newGpgKey.isNullOrEmpty()) {
                viewModel.postGpgKey(newGpgKey)
            }
        }

        viewModel.keyPosted.observe(viewLifecycleOwner) { result ->
            if (result == "true") {
                Snackbar.make(
                    requireParentFragment().requireView().findViewById(R.id.add_key),
                    "Key Added Successfully",
                    Snackbar.LENGTH_LONG
                ).show()
                lifecycleScope.launch {
                    delay(1000)
                    gpgAdapter.refresh()
                }
            } else {
                Snackbar.make(
                    requireParentFragment().requireView().findViewById(R.id.add_key),
                    result,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                viewModel.getGpgKeys().collectLatest { pagingData ->
                    recyclerView.adapter = gpgAdapter
                    gpgAdapter.submitData(pagingData)
                }
            }

            launch {
                gpgAdapter.loadStateFlow.collectLatest { loadStates ->
                    progressBar.isVisible = loadStates.refresh is LoadState.Loading
                    recyclerView.isVisible =
                        loadStates.refresh is LoadState.NotLoading && gpgAdapter.itemCount > 1
                    binding.emptyView.root.isVisible = loadStates.refresh is LoadState.NotLoading
                            && gpgAdapter.itemCount < 1 && loadStates.refresh !is LoadState.Error
                    binding.errorView.root.isVisible = loadStates.refresh is LoadState.Error
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.gpgList.adapter = null
        _binding = null
    }
}
