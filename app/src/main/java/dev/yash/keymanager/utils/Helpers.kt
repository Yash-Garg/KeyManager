package dev.yash.keymanager.utils

import android.content.Context
import androidx.appcompat.app.ActionBar

object Helpers {
    fun resetActionBar(context: Context, actionBar: ActionBar?) {
        actionBar?.show()
        actionBar?.setDisplayHomeAsUpEnabled(false)
        actionBar?.setHomeButtonEnabled(false)
        actionBar?.title = context.getAppName()
    }

    private fun Context.getAppName(): String = applicationInfo.loadLabel(packageManager).toString()
}
