package dev.yash.keymanager.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import app.yash.keymanager.databinding.SshFragmentBinding
import dev.yash.keymanager.utils.SharedPrefs

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

        val accessToken = SharedPrefs.getAccessToken(requireContext())
        val progressBar = binding.loadingIndicator
        val recyclerView = binding.sshList

        // TODO: Implement data flow and all stuff
    }
}
