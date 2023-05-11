package dev.yash.keymanager.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.yash.keymanager.data.Constants
import kotlinx.coroutines.delay

@Composable
fun DialogWithTextField(
    title: String? = null,
    label: String,
    placeholder: String,
    onDismissRequest: () -> Unit,
    onConfirm: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val uriHandler = LocalUriHandler.current
    var keyText by
        rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }

    AlertDialog(
        title = { title?.let { Text(text = it, fontWeight = FontWeight.SemiBold) } },
        confirmButton = { TextButton(onClick = { onConfirm(keyText.text) }) { Text("Confirm") } },
        dismissButton = { TextButton(onClick = onDismissRequest) { Text("Dismiss") } },
        onDismissRequest = onDismissRequest,
        text = {
            Column {
                TextButton(
                    onClick = { uriHandler.openUri(Constants.HELP_ACCESS_TOKEN) },
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "See scopes to choose while generating the token through GitHub.",
                        modifier = Modifier.padding(8.dp),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                CustomOutlinedTextField(
                    modifier = Modifier.focusRequester(focusRequester),
                    singleLine = true,
                    value = keyText,
                    onValueChange = { keyText = it },
                    leadingIcon = Icons.Filled.PrivacyTip,
                    placeholder = placeholder,
                    label = label
                )
            }
        },
    )

    LaunchedEffect(Unit) {
        delay(100)
        focusRequester.requestFocus()
    }
}
