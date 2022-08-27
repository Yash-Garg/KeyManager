package dev.yash.keymanager.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import dev.yash.keymanager.R
import dev.yash.keymanager.databinding.GpgDetailsFragmentBinding
import dev.yash.keymanager.models.GpgKey
import dev.yash.keymanager.ui.dialogs.DeleteDialogFragment
import dev.yash.keymanager.utils.Helpers
import dev.yash.keymanager.utils.viewBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GpgDetailsFragment : Fragment(R.layout.gpg_details_fragment) {
    private val binding by viewBinding(GpgDetailsFragmentBinding::bind)
    private val args: GpgDetailsFragmentArgs by navArgs()
    private val viewModel: KeyDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.top_bar, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.delete_key -> {
                            DeleteDialogFragment.newInstance().show(childFragmentManager, null)
                            true
                        }
                        android.R.id.home -> {
                            Navigation.findNavController(requireView()).navigateUp()
                            true
                        }
                        else -> false
                    }
                }
            },
            viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )

        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = ""

        val keyData = args.selectedGpgKey
        setDataInLayout(keyData)

        childFragmentManager.setFragmentResultListener("result", viewLifecycleOwner) { _, bundle ->
            val data = bundle.getBoolean("value")
            if (data) {
                viewLifecycleOwner.lifecycleScope.launch { viewModel.deleteGpgKey(keyData.id) }
            }
        }

        viewModel.gpgKeyDeleted.observe(viewLifecycleOwner) {
            if (it == "true") {
                Toast.makeText(requireContext(), "Successfully deleted key", Toast.LENGTH_SHORT)
                    .show()
                Navigation.findNavController(requireView()).navigateUp()
            } else {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setDataInLayout(data: GpgKey) {
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.ENGLISH)
            .parse(data.createdAt)
            .also {
                SimpleDateFormat("dd-MM-yyyy 'at' HH:mm 'UTC'", Locale.ENGLISH)
                    .format(it!!)
                    .toString()
                    .also { formattedDate -> binding.createdAt.setText(formattedDate) }
            }

        if (!data.expiresAt.isNullOrEmpty()) {
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.ENGLISH)
                .parse(data.expiresAt)
                .also {
                    SimpleDateFormat("dd-MM-yyyy 'at' HH:mm 'UTC'", Locale.ENGLISH)
                        .format(it!!)
                        .toString()
                        .also { formattedDate -> binding.expiresAt.setText(formattedDate) }
                }
        } else binding.expiresAt.setText(R.string.not_expires)

        binding.heading.text = data.name ?: data.id.toString()
        binding.keyId.setText(data.keyID)
        binding.idLayout.setEndIconOnClickListener {
            Helpers.copyToClipboard(requireContext(), "Key ID", data.keyID)
        }

        binding.gpgKey.setText(data.rawKey)
        binding.gpgKey.setOnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            if ((event.action and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                v.parent.requestDisallowInterceptTouchEvent(false)
            }
            return@setOnTouchListener false
        }

        binding.gpgPublicKey.setText(data.publicKey)
        binding.gpgPublicKey.setOnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            if ((event.action and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                v.parent.requestDisallowInterceptTouchEvent(false)
            }
            return@setOnTouchListener false
        }

        if (data.emails.isNotEmpty()) {
            binding.emailLayout.visibility = View.VISIBLE
            binding.emailId.setText(data.emails[0].email)
            if (data.emails[0].verified) {
                binding.emailLayout.setEndIconDrawable(R.drawable.ic_verified)
                binding.emailLayout.isEndIconVisible = true
                binding.emailLayout.setEndIconOnClickListener {
                    Toast.makeText(requireContext(), "Email is verified", Toast.LENGTH_SHORT).show()
                }
            }
        }

        if (data.canSign) binding.signCertify.visibility = View.VISIBLE
        if (data.canCertify) binding.encryptCertify.visibility = View.VISIBLE
        if (data.canEncryptComms) binding.commsCertify.visibility = View.VISIBLE
        if (data.canEncryptStorage) binding.storageCertify.visibility = View.VISIBLE
    }

    override fun onStop() {
        super.onStop()
        Helpers.resetActionBar(
            requireContext(),
            (requireActivity() as AppCompatActivity).supportActionBar
        )
    }
}
