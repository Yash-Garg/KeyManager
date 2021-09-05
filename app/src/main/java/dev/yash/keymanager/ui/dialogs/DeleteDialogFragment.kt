package dev.yash.keymanager.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import app.yash.keymanager.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DeleteDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val alertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
        alertDialogBuilder.setTitle(R.string.delete_confirmation)
        alertDialogBuilder.setPositiveButton(getString(R.string.delete), null)
        alertDialogBuilder.setNegativeButton(getString(R.string.cancel)) { _, _ -> dismiss() }
        val dialog = alertDialogBuilder.create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                // TODO: Implement delete key function
            }
        }
        return dialog
    }

    companion object {
        fun newInstance(): DeleteDialogFragment = DeleteDialogFragment()
    }
}
