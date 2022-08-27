package dev.yash.keymanager.ui.gpg

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.yash.keymanager.R
import dev.yash.keymanager.adapters.GpgAdapter
import dev.yash.keymanager.databinding.GpgFragmentBinding
import dev.yash.keymanager.utils.EventObserver
import dev.yash.keymanager.utils.viewBinding
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GpgFragment : Fragment(R.layout.gpg_fragment) {
    private val binding by viewBinding(GpgFragmentBinding::bind)
    private val viewModel: GpgViewModel by viewModels()

    @Inject lateinit var gpgAdapter: GpgAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressBar = binding.loadingIndicator
        val recyclerView = binding.gpgList
        val swipeRefreshLayout = binding.gpgSwiperefresh

        swipeRefreshLayout.setOnRefreshListener {
            gpgAdapter.refresh()
            swipeRefreshLayout.isRefreshing = false
        }

        parentFragmentManager.setFragmentResultListener("new_gpg_key", viewLifecycleOwner) {
            _,
            bundle ->
            val newGpgKey = bundle.getStringArrayList("gpg_key")
            if (!newGpgKey.isNullOrEmpty()) {
                viewModel.postGpgKey(newGpgKey[0], newGpgKey[1])
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
                        gpgAdapter.refresh()
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
                viewModel.getGpgKeys().collectLatest { pagingData ->
                    recyclerView.adapter = gpgAdapter
                    gpgAdapter.submitData(pagingData)
                }
            }

            launch {
                gpgAdapter.loadStateFlow.collectLatest { loadStates ->
                    progressBar.isVisible = loadStates.refresh is LoadState.Loading
                    recyclerView.isVisible =
                        loadStates.refresh is LoadState.NotLoading && gpgAdapter.itemCount >= 1
                    binding.emptyView.root.isVisible =
                        loadStates.refresh is LoadState.NotLoading && gpgAdapter.itemCount < 1
                    binding.errorView.root.isVisible = loadStates.refresh is LoadState.Error
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        binding.gpgList.adapter = null
    }
}
