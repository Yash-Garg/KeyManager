package dev.yash.keymanager.ui.ssh

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
import dev.yash.keymanager.utils.Helpers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SshViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {
    var keyPosted: MutableLiveData<String> = MutableLiveData()

    fun getSshKeys(): Flow<PagingData<SshKey>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SshKeysPagingSource(repository) }
        ).flow.cachedIn(viewModelScope)
    }

    fun postSshKey(key: String, title: String) = viewModelScope.launch {
        try {
            val keyModel = SshModel(title, key)
            repository.postSshKey(keyModel)
            keyPosted.value = "true"
        } catch (e: HttpException) {
            keyPosted.value = Helpers.exceptionHandler(e.code())
        }
    }
}
