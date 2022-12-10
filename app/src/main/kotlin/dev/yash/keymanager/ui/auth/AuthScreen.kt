package dev.yash.keymanager.ui.auth

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.yash.keymanager.R
import dev.yash.keymanager.ui.theme.KeyManagerTheme

@Composable
fun AuthScreen(authViewModel: AuthViewModel = viewModel()) {
    val authState by authViewModel.authState.collectAsState()

    when (authState.isAuthenticated) {
        true -> {}
        false -> {
            val getAuthCodeFromResult =
                rememberLauncherForActivityResult(
                    ActivityResultContracts.StartActivityForResult()
                ) { result ->
                    if (result.resultCode == RESULT_OK) {
                        authViewModel.requestAccessToken(requireNotNull(result.data))
                    }
                }

            AuthenticationUI(
                onSignIn = { getAuthCodeFromResult.launch(authViewModel.getAuthReqIntent()) }
            )
        }
    }
}

@Composable
fun AuthenticationUI(onSignIn: () -> Unit) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (icon, button) = createRefs()

        Icon(
            modifier =
                Modifier.size(90.dp).constrainAs(icon) {
                    top.linkTo(parent.top)
                    bottom.linkTo(button.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            painter = painterResource(R.drawable.ic_github),
            contentDescription = stringResource(id = R.string.github_logo_desc)
        )

        Button(
            onClick = onSignIn,
            modifier =
                Modifier.fillMaxWidth(.9f).padding(vertical = 12.dp).constrainAs(button) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Text(
                text = stringResource(id = R.string.signin_button_text),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, device = "id:pixel_4_xl")
@Composable
fun AuthScreenPreview() {
    KeyManagerTheme { AuthenticationUI(onSignIn = {}) }
}
