package dev.yash.keymanager.utils

import android.content.ClipData
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.browser.customtabs.CustomTabsIntent

object Helpers {
    fun resetActionBar(context: Context, actionBar: ActionBar?) {
        actionBar?.show()
        actionBar?.setDisplayHomeAsUpEnabled(false)
        actionBar?.setHomeButtonEnabled(false)
        actionBar?.title = context.getAppName()
    }

    private fun Context.getAppName(): String = applicationInfo.loadLabel(packageManager).toString()

    fun copyToClipboard(context: Context, label: String, text: String) {
        val clipboard =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Copied key to clipboard", Toast.LENGTH_SHORT).show()
    }

    fun launchURL(context: Context, url: String) {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.launchUrl(context, Uri.parse(url))
    }
}