package dev.yash.keymanager.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import app.yash.keymanager.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class NewKeyDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
        alertDialogBuilder.setTitle(R.string.create_ssh)
        alertDialogBuilder.setView(R.layout.create_dialog)
        alertDialogBuilder.setPositiveButton(getString(R.string.create_button), null)
        alertDialogBuilder.setNegativeButton(getString(R.string.cancel)) { _, _ -> dismiss() }
        val dialog = alertDialogBuilder.create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                // TODO
            }
        }
        return dialog
    }

    companion object {
        fun newInstance(): NewKeyDialogFragment = NewKeyDialogFragment()
    }
}
