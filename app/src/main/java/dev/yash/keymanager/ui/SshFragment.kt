package dev.yash.keymanager.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import app.yash.keymanager.R
import app.yash.keymanager.databinding.SshFragmentBinding
import dev.yash.keymanager.utils.SharedPrefs

class SshFragment : Fragment() {
    private val viewModel: SshViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.ssh_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = SshFragmentBinding.inflate(layoutInflater)

        val accessToken = SharedPrefs.getAccessToken(requireContext())
        val progressBar = binding.loadingIndicator
        val recyclerView = binding.sshList

        // TODO: Implement data flow and all stuff
    }
}
