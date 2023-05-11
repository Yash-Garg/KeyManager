package dev.yash.keymanager.ui.keys

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import androidx.paging.compose.items
import dev.yash.keymanager.data.models.GpgKey
import dev.yash.keymanager.ui.common.LoadError

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GpgKeyListScreen(
    lazyPagingItems: LazyPagingItems<GpgKey>,
    modifier: Modifier = Modifier,
    onKeyClick: (Long) -> Unit
) {
    val refreshLoadState = lazyPagingItems.loadState.refresh
    val isRefreshing = refreshLoadState is LoadState.Loading
    val pullRefreshState = rememberPullRefreshState(isRefreshing, lazyPagingItems::refresh)

    Box(modifier = modifier.pullRefresh(pullRefreshState)) {
        if (lazyPagingItems.itemCount == 0 && refreshLoadState is LoadState.Error) {
            LoadError(message = "Failed to load data.")
        } else {
            if (lazyPagingItems.itemCount == 0 && !isRefreshing) {
                LoadError(message = "So many locks but no keys :(")
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(
                        count = lazyPagingItems.itemCount,
                        key = lazyPagingItems.itemKey(key = { key -> key.id }),
                        contentType = lazyPagingItems.itemContentType()
                    ) { index ->
                        val item = lazyPagingItems[index]
                        if (item != null) {
                            GpgKeyCard(key = item, onKeyClick = { onKeyClick(item.id) })
                        }
                    }
                    if (lazyPagingItems.loadState.append == LoadState.Loading) {
                        item { LinearProgressIndicator(modifier = modifier.fillMaxWidth(.8f)) }
                    }
                }
            }
        }
        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = MaterialTheme.colorScheme.surface,
            contentColor = contentColorFor(MaterialTheme.colorScheme.surface),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GpgKeyCard(key: GpgKey, onKeyClick: () -> Unit) {
    Card(
        onClick = onKeyClick,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp, vertical = 6.dp)
    ) {
        Box(Modifier.fillMaxSize().padding(12.dp)) {
            Column {
                Text(key.name ?: key.id.toString(), fontWeight = FontWeight.SemiBold)
                Text("Key ID - ${key.keyID}")
                Text(key.publicKey, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}
