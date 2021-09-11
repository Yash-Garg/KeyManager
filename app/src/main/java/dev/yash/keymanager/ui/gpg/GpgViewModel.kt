package dev.yash.keymanager.ui.gpg

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
import dev.yash.keymanager.utils.Event
import dev.yash.keymanager.utils.Helpers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class GpgViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {
    val keyPosted: MutableLiveData<Event<String>> = MutableLiveData()

    fun getGpgKeys(): Flow<PagingData<GpgKey>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GpgKeysPagingSource(repository) }
        ).flow.cachedIn(viewModelScope)
    }

    fun postGpgKey(key: String) = viewModelScope.launch {
        try {
            val keyModel = GpgModel(key)
            repository.postGpgKey(keyModel)
            keyPosted.postValue(Event("true"))
        } catch (e: HttpException) {
            keyPosted.postValue(Event(Helpers.exceptionHandler(e.code())))
        }
    }
}
