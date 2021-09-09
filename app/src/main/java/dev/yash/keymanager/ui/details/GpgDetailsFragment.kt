package dev.yash.keymanager.ui.details

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import app.yash.keymanager.R
import app.yash.keymanager.databinding.GpgDetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.yash.keymanager.ui.dialogs.DeleteDialogFragment
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
        actionBar?.title = ""

//        SimpleDateFormat(
//            "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
//            Locale.US
//        ).parse(keyData.createdAt)
//            .also {
//                SimpleDateFormat("dd/mm/yyyy 'at' hh:mm a", Locale.ENGLISH)
//                    .format(it!!).toString().also { formattedDate ->
//                        binding.createdAt.setText(formattedDate)
//                    }
//            }
//
//        if (!keyData.expiresAt.isNullOrEmpty()) {
//            SimpleDateFormat(
//                "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
//                Locale.ENGLISH
//            ).parse(keyData.expiresAt)
//                .also {
//                    SimpleDateFormat("dd/mm/yyyy 'at' hh:mm a", Locale.ENGLISH)
//                        .format(it!!).toString().also { formattedDate ->
//                            binding.expiresAt.setText(formattedDate)
//                        }
//                }
//        } else binding.expiresAt.setText(R.string.not_expires)

        binding.keyId.setText(keyData.keyID)
        binding.idLayout.setEndIconOnClickListener {
            Helpers.copyToClipboard(requireContext(), "Key ID", keyData.keyID)
        }

        binding.gpgKey.setText(keyData.rawKey)
        binding.gpgPublicKey.setText(keyData.publicKey)

        if (keyData.canSign) binding.signCertify.visibility = View.VISIBLE
        if (keyData.canCertify) binding.encryptCertify.visibility = View.VISIBLE
        if (keyData.canEncryptComms) binding.commsCertify.visibility = View.VISIBLE
        if (keyData.canEncryptStorage) binding.storageCertify.visibility = View.VISIBLE
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
