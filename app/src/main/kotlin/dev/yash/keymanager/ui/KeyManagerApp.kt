package dev.yash.keymanager.ui

import androidx.compose.runtime.Composable
import dev.yash.keymanager.ui.auth.AuthScreen
import dev.yash.keymanager.ui.theme.KeyManagerTheme

@Composable fun KeyManagerApp() = KeyManagerTheme { AuthScreen() }
