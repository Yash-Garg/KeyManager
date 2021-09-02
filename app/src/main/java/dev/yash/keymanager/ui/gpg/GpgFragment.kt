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
import app.yash.keymanager.databinding.GpgFragmentBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.yash.keymanager.adapters.GpgAdapter
import dev.yash.keymanager.models.GpgModel
import dev.yash.keymanager.ui.dialogs.GpgNewKeyDialogFragment
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
        val addFab = binding.addGpg
        val swipeRefreshLayout = binding.gpgSwiperefresh

        swipeRefreshLayout.setOnRefreshListener {
            gpgAdapter.refresh()
            swipeRefreshLayout.isRefreshing = false
        }

        addFab.setOnClickListener {
            GpgNewKeyDialogFragment.newInstance().show(childFragmentManager, null)
        }

        childFragmentManager.setFragmentResultListener(
            "new_gpg_key",
            viewLifecycleOwner
        ) { _, bundle ->
            val newGpgKey = bundle.getString("gpg_key")
            if (!newGpgKey.isNullOrEmpty()) {
                viewModel.postGpgKey(GpgModel(newGpgKey))
                viewModel.keyPosted.observe(viewLifecycleOwner) { result ->
                    if (result == true) {
                        Snackbar.make(view, "Key Added Successfully", Snackbar.LENGTH_SHORT).show()
                        lifecycleScope.launch {
                            delay(1000)
                            gpgAdapter.refresh()
                        }
                    } else {
                        Snackbar.make(view, "Some error occured", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.getGpgKeys().collectLatest { pagingData ->
                recyclerView.adapter = gpgAdapter
                gpgAdapter.submitData(pagingData)
            }
        }

        lifecycleScope.launch {
            gpgAdapter.loadStateFlow.collectLatest { loadStates ->
                progressBar.isVisible = loadStates.refresh is LoadState.Loading
                recyclerView.isVisible = loadStates.refresh is LoadState.NotLoading
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
