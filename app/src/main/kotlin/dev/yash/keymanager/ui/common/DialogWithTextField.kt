package dev.yash.keymanager.ui.common

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import dev.yash.keymanager.data.models.KeyType
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun DialogWithTextField(
    title: String,
    onDismissRequest: () -> Unit,
    onConfirm: (String, String, KeyType) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    var keyType by rememberSaveable { mutableStateOf(KeyType.SSH) }
    var keyTitle by
        rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    var keyText by
        rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }

    AlertDialog(
        title = { Text(text = title, fontWeight = FontWeight.SemiBold) },
        confirmButton = {
            TextButton(onClick = { onConfirm(keyTitle.text, keyText.text, keyType) }) {
                Text("Confirm")
            }
        },
        dismissButton = { TextButton(onClick = onDismissRequest) { Text("Dismiss") } },
        onDismissRequest = onDismissRequest,
        text = {
            Column {
                Row(
                    modifier = Modifier.horizontalScroll(state = rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    KeyType.all.forEach {
                        InputChip(
                            selected = keyType == it,
                            onClick = { keyType = it },
                            leadingIcon = {
                                if (keyType == it) {
                                    Icon(Icons.Filled.Done, null)
                                }
                            },
                            label = { Text(text = it.title) }
                        )
                    }
                }
                Spacer(modifier = Modifier.size(8.dp))
                CustomOutlinedTextField(
                    modifier = Modifier.focusRequester(focusRequester),
                    singleLine = true,
                    value = keyTitle,
                    onValueChange = { keyTitle = it },
                    leadingIcon = Icons.Filled.Title,
                    placeholder = "Example key",
                    label = "Title"
                )
                Spacer(modifier = Modifier.size(8.dp))
                CustomOutlinedTextField(
                    singleLine = true,
                    value = keyText,
                    onValueChange = { keyText = it },
                    leadingIcon = Icons.Filled.Key,
                    placeholder = "",
                    label = "Key"
                )
            }
        },
    )

    LaunchedEffect(Unit) {
        delay(100)
        focusRequester.requestFocus()
    }
}
