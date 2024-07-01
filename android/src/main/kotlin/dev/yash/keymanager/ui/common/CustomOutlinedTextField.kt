package dev.yash.keymanager.ui.common

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomOutlinedTextField(
    modifier: Modifier = Modifier,
    singleLine: Boolean = false,
    placeholder: String,
    label: String,
    value: TextFieldValue,
    leadingIcon: ImageVector,
    onValueChange: (TextFieldValue) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        placeholder = { Text(placeholder) },
        value = value,
        singleLine = singleLine,
        onValueChange = onValueChange,
        leadingIcon = { Icon(leadingIcon, null) },
        label = { Text(label) }
    )
}
