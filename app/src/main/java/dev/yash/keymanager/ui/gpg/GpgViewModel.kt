package dev.yash.keymanager.ui.gpg

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
import dev.yash.keymanager.models.GpgKey
import dev.yash.keymanager.models.GpgModel
import dev.yash.keymanager.paging.GpgKeysPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GpgViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {
    var keyPosted: MutableLiveData<Boolean> = MutableLiveData()

    fun getGpgKeys(): Flow<PagingData<GpgKey>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GpgKeysPagingSource(repository) }
        ).flow.cachedIn(viewModelScope)
    }

    fun postGpgKey(key: GpgModel) = viewModelScope.launch {
        try {
            repository.postGpgKey(key)
            keyPosted.value = true
        } catch (e: Exception) {
            Log.e("ERROR", e.toString())
        }
    }
}
