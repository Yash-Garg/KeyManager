package dev.yash.keymanager.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.yash.keymanager.api.GitHubService
import dev.yash.keymanager.models.SshKey
import dev.yash.keymanager.paging.SshKeysPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val service: GitHubService) : ViewModel() {
    fun getKeysSSH(accessToken: String): Flow<PagingData<SshKey>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SshKeysPagingSource(service, accessToken) }
        ).flow.cachedIn(viewModelScope)
    }
}
