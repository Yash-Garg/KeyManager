package dev.yash.keymanager.ui.ssh

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.yash.keymanager.api.GithubRepository
import dev.yash.keymanager.models.SshKey
import dev.yash.keymanager.models.SshModel
import dev.yash.keymanager.paging.SshKeysPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SshViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {
    var keyPosted: MutableLiveData<Boolean> = MutableLiveData()

    fun getSshKeys(): Flow<PagingData<SshKey>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SshKeysPagingSource(repository) }
        ).flow.cachedIn(viewModelScope)
    }

    fun postKey(key: SshModel) = viewModelScope.launch {
        try {
            repository.postSshKey(key)
            keyPosted.value = true
        } catch (e: Exception) {
            Log.e("ERROR", e.toString())
        }
    }
}
