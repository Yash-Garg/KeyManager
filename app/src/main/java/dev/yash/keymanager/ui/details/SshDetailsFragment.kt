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
import dev.yash.keymanager.databinding.SshDetailsFragmentBinding
import dev.yash.keymanager.models.SshKey
import dev.yash.keymanager.ui.dialogs.DeleteDialogFragment
import dev.yash.keymanager.utils.Helpers
import dev.yash.keymanager.utils.viewBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SshDetailsFragment : Fragment(R.layout.ssh_details_fragment) {
    private val binding by viewBinding(SshDetailsFragmentBinding::bind)
    private val args: SshDetailsFragmentArgs by navArgs()
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

        val keyData = args.selectedSshKey
        setDataInLayout(keyData)

        childFragmentManager.setFragmentResultListener("result", viewLifecycleOwner) { _, bundle ->
            val data = bundle.getBoolean("value")
            if (data) {
                viewLifecycleOwner.lifecycleScope.launch { viewModel.deleteSshKey(keyData.id) }
            }
        }

        viewModel.sshKeyDeleted.observe(viewLifecycleOwner) {
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
    private fun setDataInLayout(data: SshKey) {
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH).parse(data.createdAt).also {
            SimpleDateFormat("dd-MM-yyyy 'at' HH:mm 'UTC'", Locale.ENGLISH)
                .format(it!!)
                .toString()
                .also { formattedDate -> binding.createdAt.setText(formattedDate) }
        }

        binding.keyId.setText(data.id.toString())
        binding.idLayout.setEndIconOnClickListener {
            Helpers.copyToClipboard(requireContext(), "Key ID", data.id.toString())
        }

        binding.heading.text = data.title
        binding.keyUrl.setText(data.url)

        binding.sshKey.setText(data.key)
        binding.sshKey.setOnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            if ((event.action and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                v.parent.requestDisallowInterceptTouchEvent(false)
            }
            return@setOnTouchListener false
        }

        binding.keyLayout.setEndIconOnClickListener {
            Helpers.copyToClipboard(requireContext(), "SSH Key", data.key)
        }

        if (data.readOnly) binding.readOnlyChip.visibility = View.VISIBLE
        if (data.verified) binding.verifiedChip.visibility = View.VISIBLE
    }

    override fun onStop() {
        super.onStop()
        Helpers.resetActionBar(
            requireContext(),
            (requireActivity() as AppCompatActivity).supportActionBar
        )
    }
}
