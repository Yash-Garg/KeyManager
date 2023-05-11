package dev.yash.keymanager.ui.auth

import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.yash.keymanager.R
import dev.yash.keymanager.ui.common.DialogWithTextField
import dev.yash.keymanager.ui.theme.KeyManagerTheme

@Composable
fun AuthScreen(viewModel: AuthViewModel = viewModel(), onAuthNavigate: () -> Unit) {
    val authState by viewModel.authState.collectAsState()

    when (authState.isAuthenticated) {
        true -> LaunchedEffect(Unit) { onAuthNavigate() }
        false -> {
            val getAuthCodeFromResult =
                rememberLauncherForActivityResult(
                    ActivityResultContracts.StartActivityForResult()
                ) { result ->
                    if (result.resultCode == RESULT_OK) {
                        viewModel.requestAccessToken(requireNotNull(result.data))
                    }
                }

            AuthenticationUI(
                onSignIn = { getAuthCodeFromResult.launch(viewModel.getAuthReqIntent()) },
                onAccessToken = { token -> viewModel.saveToken(token) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticationUI(onSignIn: () -> Unit, onAccessToken: (String) -> Unit) {
    Scaffold {
        val context = LocalContext.current
        val addTokenDialog = remember { mutableStateOf(false) }

        ConstraintLayout(modifier = Modifier.fillMaxSize().padding(it)) {
            val (icon, button, altSign) = createRefs()
            Icon(
                modifier =
                    Modifier.size(90.dp).constrainAs(icon) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                painter = painterResource(R.drawable.ic_github),
                contentDescription = stringResource(id = R.string.github_logo_desc)
            )

            Button(
                onClick = onSignIn,
                modifier =
                    Modifier.fillMaxWidth(.9f).padding(horizontal = 12.dp).constrainAs(button) {
                        top.linkTo(icon.bottom)
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

            TextButton(
                modifier =
                    Modifier.constrainAs(altSign) {
                        top.linkTo(button.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                onClick = { addTokenDialog.value = true }
            ) {
                Text(
                    text = "Use access token instead",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        if (addTokenDialog.value) {
            DialogWithTextField(
                label = "Access Token",
                placeholder = "ghp_1234567890",
                onDismissRequest = { addTokenDialog.value = false },
                onConfirm = { token ->
                    if (token.isNotEmpty() && token.contains("ghp_")) {
                        onAccessToken(token)
                    } else {
                        Toast.makeText(context, "Token cannot be empty", Toast.LENGTH_SHORT).show()
                        addTokenDialog.value = false
                        return@DialogWithTextField
                    }
                }
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, device = "id:pixel_4_xl")
@Composable
fun AuthScreenPreview() {
    KeyManagerTheme { AuthenticationUI(onSignIn = {}, onAccessToken = {}) }
}
