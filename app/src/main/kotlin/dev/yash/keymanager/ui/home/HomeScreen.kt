package dev.yash.keymanager.ui.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import dev.yash.keymanager.data.models.GpgKey
import dev.yash.keymanager.data.models.SshKey

@Suppress("UNCHECKED_CAST")
@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    val sshKeys = viewModel.sshKeys.collectAsLazyPagingItems() as LazyPagingItems<SshKey>
    val gpgKeys = viewModel.gpgKeys.collectAsLazyPagingItems() as LazyPagingItems<GpgKey>

    LazyColumn {
        items(sshKeys) { key ->
            if (key != null) {
                Text(text = key.id.toString())
            }
        }
        items(gpgKeys) { key ->
            if (key != null) {
                Text(text = key.id.toString())
            }
        }
    }
}
