package dev.yash.keymanager.ui.auth

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.yash.keymanager.R
import dev.yash.keymanager.ui.theme.KeyManagerTheme

@Composable
fun AuthScreen(authViewModel: AuthViewModel = viewModel()) {
    val authState by authViewModel.authStatus.collectAsState(initial = AuthStatus.UNAUTHENTICATED)

    when (authState) {
        AuthStatus.AUTHENTICATED -> TODO("Navigate to HomeScreen")
        AuthStatus.UNAUTHENTICATED -> LoginUI()
    }
}

@Composable
fun LoginUI() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Icon(painter = painterResource(R.drawable.ic_github), contentDescription = null)
    }
}

@Preview(showSystemUi = true, showBackground = true, device = "id:pixel_4_xl")
@Composable
fun AuthScreenPreview() {
    KeyManagerTheme { LoginUI() }
}
