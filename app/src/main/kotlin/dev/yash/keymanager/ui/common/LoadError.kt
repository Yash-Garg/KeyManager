package dev.yash.keymanager.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun LoadError(
    modifier: Modifier = Modifier,
    message: String = "Something went wrong!",
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).then(modifier)
    ) {
        Text(text = message, textAlign = TextAlign.Center)
    }
}
