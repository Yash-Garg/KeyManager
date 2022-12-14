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
import androidx.paging.compose.items
import dev.yash.keymanager.data.models.SshKey
import dev.yash.keymanager.ui.common.LoadError

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SshKeyListScreen(
    lazyPagingItems: LazyPagingItems<SshKey>,
    lazyPagingSigningItems: LazyPagingItems<SshKey>,
    modifier: Modifier = Modifier
) {
    val refreshLoadState = lazyPagingItems.loadState.refresh
    val refreshSigningLoadState = lazyPagingSigningItems.loadState.refresh

    val isRefreshing =
        refreshLoadState is LoadState.Loading || refreshSigningLoadState is LoadState.Loading
    val pullRefreshState =
        rememberPullRefreshState(
            isRefreshing,
            {
                lazyPagingItems.refresh()
                lazyPagingSigningItems.refresh()
            }
        )

    Box(modifier = modifier.pullRefresh(pullRefreshState)) {
        if (refreshLoadState is LoadState.Error || refreshSigningLoadState is LoadState.Error) {
            LoadError(message = "Failed to load data.")
        } else {
            if (lazyPagingItems.itemCount == 0 && !isRefreshing) {
                LoadError(message = "So many locks but no keys :(")
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(lazyPagingItems, key = { key -> key.id }) { key ->
                        if (key != null) {
                            SshKeyCard(key = key, onKeyClick = {})
                        }
                    }

                    items(lazyPagingSigningItems, key = { key -> key.id }) { key ->
                        if (key != null) {
                            SshKeyCard(key = key, onKeyClick = {})
                        }
                    }

                    if (
                        lazyPagingItems.loadState.append == LoadState.Loading ||
                            lazyPagingSigningItems.loadState.append == LoadState.Loading
                    ) {
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
fun SshKeyCard(key: SshKey, onKeyClick: () -> Unit) {
    Card(
        onClick = onKeyClick,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp, vertical = 6.dp)
    ) {
        Box(Modifier.fillMaxSize().padding(12.dp)) {
            Column {
                Text(key.title, fontWeight = FontWeight.SemiBold)
                Text("Key ID - ${key.id}")
                Text(key.key, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}
