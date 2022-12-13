package dev.yash.keymanager.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog

@Composable
fun DialogWithTextField(title: String, onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = onDismissRequest) {
        Column {
            Text(text = title)
            Row {
                TextButton(onClick = { /*TODO*/}) {}

                TextButton(onClick = { /*TODO*/}) {}
            }
        }
    }
}
