package dev.yash.keymanager.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import app.yash.keymanager.databinding.SshDetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SshDetailsFragment : Fragment() {
    private var _binding: SshDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: SshDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SshDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val keyData = args.selectedSshKey
        Log.d("SELECTED KEY DATA", keyData.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
