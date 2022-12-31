package dev.yash.keymanager.ui.keys

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.yash.keymanager.data.models.KeyType.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KeyDetailsScreen(modifier: Modifier = Modifier, keyId: Long) {
    Scaffold(modifier = Modifier.then(modifier)) {
        MediumTopAppBar(title = { Text(text = keyId.toString()) })
    }
}
