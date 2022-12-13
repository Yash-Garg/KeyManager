package dev.yash.keymanager.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoadError(
    modifier: Modifier = Modifier,
    message: String = "Something went wrong!",
) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize().then(modifier)) {
        Text(text = message)
    }
}
