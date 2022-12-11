package dev.yash.keymanager.ui.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import dev.yash.keymanager.data.models.GpgKey
import dev.yash.keymanager.data.models.SshKey

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("UNCHECKED_CAST")
@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    val sshKeys = viewModel.sshKeys.collectAsLazyPagingItems() as LazyPagingItems<SshKey>
    val gpgKeys = viewModel.gpgKeys.collectAsLazyPagingItems() as LazyPagingItems<GpgKey>

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/}) { Icon(Icons.Filled.Add, null) }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        SshKeyListScreen(lazyPagingItems = sshKeys, modifier = Modifier.padding(it))
    }
}

@Composable
fun SshKeyListScreen(lazyPagingItems: LazyPagingItems<SshKey>, modifier: Modifier = Modifier) {
    val refreshLoadState = lazyPagingItems.loadState.refresh
    val isRefreshing = refreshLoadState is LoadState.Loading

    if (lazyPagingItems.itemCount == 0 && refreshLoadState is LoadState.Error) {
        Text(text = "Failed to load data.")
    } else {
        LazyColumn(modifier = modifier) {
            items(lazyPagingItems, key = { key -> key.id }) { key ->
                if (key != null) {
                    Text(text = key.id.toString())
                }
            }
            if (lazyPagingItems.loadState.append == LoadState.Loading) {
                item { LinearProgressIndicator(modifier = modifier.fillMaxWidth(.8f)) }
            }
        }
    }
}
