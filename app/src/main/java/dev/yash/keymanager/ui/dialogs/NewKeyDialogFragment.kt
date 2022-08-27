package dev.yash.keymanager.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import dev.yash.keymanager.R

class SshNewKeyDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
        alertDialogBuilder.setTitle(R.string.create_ssh)
        alertDialogBuilder.setView(R.layout.create_key_dialog)
        alertDialogBuilder.setPositiveButton(getString(R.string.create_button), null)
        alertDialogBuilder.setNegativeButton(getString(R.string.cancel)) { _, _ -> dismiss() }
        val dialog = alertDialogBuilder.create()
        dialog.window?.setBackgroundDrawableResource(R.color.material_dark)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val key = dialog.findViewById<TextInputEditText>(R.id.key_tiet)
                val keyTitle = dialog.findViewById<TextInputEditText>(R.id.keyName_tiet)
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
        alertDialogBuilder.setView(R.layout.create_key_dialog)
        alertDialogBuilder.setPositiveButton(getString(R.string.create_button), null)
        alertDialogBuilder.setNegativeButton(getString(R.string.cancel)) { _, _ -> dismiss() }
        val dialog = alertDialogBuilder.create()
        dialog.window?.setBackgroundDrawableResource(R.color.material_dark)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val keyName = dialog.findViewById<TextInputEditText>(R.id.keyName_tiet)
                val gpgKeyTv = dialog.findViewById<TextInputEditText>(R.id.key_tiet)
                val bundle = Bundle()
                if (!gpgKeyTv?.text.isNullOrEmpty()) {
                    bundle.putStringArrayList(
                        "gpg_key",
                        arrayListOf(gpgKeyTv?.text.toString(), keyName?.text.toString())
                    )
                    setFragmentResult("new_gpg_key", bundle)
                    dialog.dismiss()
                } else {
                    gpgKeyTv?.error = "This field is required"
                }
            }
        }
        return dialog
    }

    companion object {
        fun newInstance(): GpgNewKeyDialogFragment = GpgNewKeyDialogFragment()
    }
}
