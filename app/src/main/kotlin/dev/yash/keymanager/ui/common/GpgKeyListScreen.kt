package dev.yash.keymanager.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import dev.yash.keymanager.data.models.GpgKey

@Composable
fun GpgKeyListScreen(lazyPagingItems: LazyPagingItems<GpgKey>, modifier: Modifier = Modifier) {
    val refreshLoadState = lazyPagingItems.loadState.refresh
    val isRefreshing = refreshLoadState is LoadState.Loading

    if (lazyPagingItems.itemCount == 0 && refreshLoadState is LoadState.Error) {
        Text(text = "Failed to load data.")
    } else {
        LazyColumn(modifier = modifier) {
            items(lazyPagingItems, key = { key -> key.id }) { key ->
                if (key != null) {
                    GpgKeyCard(key = key, onKeyClick = { /* TODO */})
                }
            }
            if (lazyPagingItems.loadState.append == LoadState.Loading) {
                item { LinearProgressIndicator(modifier = modifier.fillMaxWidth(.8f)) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GpgKeyCard(key: GpgKey, onKeyClick: () -> Unit) {
    Card(onClick = onKeyClick, modifier = Modifier.fillMaxWidth().padding(15.dp)) {
        Box(Modifier.fillMaxSize().padding(12.dp)) {
            Column {
                Text(key.name ?: key.id.toString(), fontWeight = FontWeight.SemiBold)
                Text("Key ID - ${key.keyID}")
                Text(key.publicKey, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}
