package dev.yash.keymanager.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import app.yash.keymanager.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

class SshNewKeyDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
        alertDialogBuilder.setTitle(R.string.create_ssh)
        alertDialogBuilder.setView(R.layout.ssh_create_dialog)
        alertDialogBuilder.setPositiveButton(getString(R.string.create_button), null)
        alertDialogBuilder.setNegativeButton(getString(R.string.cancel)) { _, _ -> dismiss() }
        val dialog = alertDialogBuilder.create()
        dialog.window?.setBackgroundDrawableResource(R.color.material_dark)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val key = dialog.findViewById<TextInputEditText>(R.id.ssh_key_value)
                val keyTitle = dialog.findViewById<TextInputEditText>(R.id.ssh_key_name_field)
                val bundle = Bundle()
                if (!key?.text.isNullOrEmpty()) {
                    bundle.putStringArrayList(
                        "ssh_key",
                        arrayListOf(key?.text.toString(), keyTitle?.text.toString())
                    )
                    setFragmentResult("new_ssh_key", bundle)
                    dialog.dismiss()
                } else {
                    key?.error = "This field is required"
                }
            }
        }
        return dialog
    }

    companion object {
        fun newInstance(): SshNewKeyDialogFragment = SshNewKeyDialogFragment()
    }
}

class GpgNewKeyDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
        alertDialogBuilder.setTitle(R.string.create_gpg)
        alertDialogBuilder.setView(R.layout.gpg_create_dialog)
        alertDialogBuilder.setPositiveButton(getString(R.string.create_button), null)
        alertDialogBuilder.setNegativeButton(getString(R.string.cancel)) { _, _ -> dismiss() }
        val dialog = alertDialogBuilder.create()
        dialog.window?.setBackgroundDrawableResource(R.color.material_dark)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val editTextView = dialog.findViewById<TextInputEditText>(R.id.gpg_key_value)
                if (!editTextView?.text.isNullOrEmpty()) {
                    setFragmentResult(
                        "new_gpg_key",
                        bundleOf("gpg_key" to editTextView?.text.toString())
                    )
                    dialog.dismiss()
                } else {
                    editTextView?.error = "This field is required"
                }
            }
        }
        return dialog
    }

    companion object {
        fun newInstance(): GpgNewKeyDialogFragment = GpgNewKeyDialogFragment()
    }
}
