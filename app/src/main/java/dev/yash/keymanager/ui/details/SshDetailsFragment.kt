package dev.yash.keymanager.ui.details

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import app.yash.keymanager.R
import app.yash.keymanager.databinding.SshDetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.yash.keymanager.ui.dialogs.DeleteDialogFragment
import dev.yash.keymanager.utils.Helpers
import java.text.SimpleDateFormat
import java.util.*

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
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        val keyData = args.selectedSshKey

        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = ""

        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH).parse(keyData.createdAt)
            .also {
                SimpleDateFormat("dd/mm/yyyy 'at' hh:mm a", Locale.ENGLISH)
                    .format(it!!).toString().also { formattedDate ->
                        binding.createdAt.setText(formattedDate)
                    }
            }

        binding.keyId.setText(keyData.id.toString())
        binding.idLayout.setEndIconOnClickListener {
            Helpers.copyToClipboard(requireContext(), "Key ID", keyData.id.toString())
        }

        binding.heading.text = keyData.title
        binding.keyUrl.setText(keyData.url)

        binding.sshKey.setText(keyData.key)
        binding.keyLayout.setEndIconOnClickListener {
            Helpers.copyToClipboard(requireContext(), "SSH Key", keyData.key)
        }

        if (keyData.readOnly) binding.readOnlyChip.visibility = View.VISIBLE
        if (keyData.verified) binding.verifiedChip.visibility = View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.top_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.delete_key -> {
            DeleteDialogFragment.newInstance().show(childFragmentManager, null)
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
