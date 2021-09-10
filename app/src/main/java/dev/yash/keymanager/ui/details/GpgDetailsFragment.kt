package dev.yash.keymanager.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import app.yash.keymanager.R
import app.yash.keymanager.databinding.GpgDetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.yash.keymanager.ui.dialogs.DeleteDialogFragment
import dev.yash.keymanager.utils.Helpers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class GpgDetailsFragment : Fragment() {
    private var _binding: GpgDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: GpgDetailsFragmentArgs by navArgs()
    private val viewModel: KeyDetailsViewModel by viewModels()

    override
    fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GpgDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        val keyData = args.selectedGpgKey

        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = ""

        SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.ENGLISH
        ).parse(keyData.createdAt).also {
            SimpleDateFormat("dd-MM-yyyy 'at' HH:mm 'UTC'", Locale.ENGLISH)
                .format(it!!).toString().also { formattedDate ->
                    binding.createdAt.setText(formattedDate)
                }
        }

        if (!keyData.expiresAt.isNullOrEmpty()) {
            SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.ENGLISH
            ).parse(keyData.expiresAt).also {
                SimpleDateFormat("dd-MM-yyyy 'at' HH:mm 'UTC'", Locale.ENGLISH)
                    .format(it!!).toString().also { formattedDate ->
                        binding.expiresAt.setText(formattedDate)
                    }
            }
        } else binding.expiresAt.setText(R.string.not_expires)

        "ID - ${keyData.id}".also { binding.heading.text = it }
        binding.keyId.setText(keyData.keyID)
        binding.idLayout.setEndIconOnClickListener {
            Helpers.copyToClipboard(requireContext(), "Key ID", keyData.keyID)
        }

        binding.gpgKey.setText(keyData.rawKey)
        binding.gpgKey.setOnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            if ((event.action and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                v.parent.requestDisallowInterceptTouchEvent(false)
            }
            return@setOnTouchListener false
        }

        binding.gpgPublicKey.setText(keyData.publicKey)
        binding.gpgPublicKey.setOnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            if ((event.action and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                v.parent.requestDisallowInterceptTouchEvent(false)
            }
            return@setOnTouchListener false
        }

        if (!keyData.emails.isNullOrEmpty()) {
            binding.emailLayout.visibility = View.VISIBLE
            binding.emailId.setText(keyData.emails[0].email)
            if (keyData.emails[0].verified) {
                binding.emailLayout.setEndIconDrawable(R.drawable.ic_verified)
                binding.emailLayout.isEndIconVisible = true
                binding.emailLayout.setEndIconOnClickListener {
                    Toast.makeText(requireContext(), "Email is verified", Toast.LENGTH_SHORT).show()
                }
            }
        }

        if (keyData.canSign) binding.signCertify.visibility = View.VISIBLE
        if (keyData.canCertify) binding.encryptCertify.visibility = View.VISIBLE
        if (keyData.canEncryptComms) binding.commsCertify.visibility = View.VISIBLE
        if (keyData.canEncryptStorage) binding.storageCertify.visibility = View.VISIBLE

        childFragmentManager.setFragmentResultListener(
            "result",
            viewLifecycleOwner
        ) { _, bundle ->
            val data = bundle.getBoolean("value")
            if (data) {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.deleteGpgKey(keyData.id)
                    // TODO
                }
            }
        }
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
