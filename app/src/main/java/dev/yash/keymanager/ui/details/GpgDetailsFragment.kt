package dev.yash.keymanager.ui.details

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import app.yash.keymanager.R
import app.yash.keymanager.databinding.GpgDetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.yash.keymanager.utils.Helpers

@AndroidEntryPoint
class GpgDetailsFragment : Fragment() {
    private var _binding: GpgDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: GpgDetailsFragmentArgs by navArgs()

    override
    fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GpgDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        val keyData = args.selectedGpgKey

        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = keyData.keyID

        Log.d("SELECTED KEY DATA", keyData.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.top_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.delete_key -> {
            Toast.makeText(requireContext(), "Delete Gpg Key", Toast.LENGTH_SHORT).show()
            true
        }
        android.R.id.home -> {
            Navigation.findNavController(requireView()).navigateUp()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Helpers.resetActionBar(
            requireContext(),
            (requireActivity() as AppCompatActivity).supportActionBar
        )
        _binding = null
    }
}
