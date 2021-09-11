package dev.yash.keymanager.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.yash.keymanager.api.GithubRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class KeyDetailsViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {
    var sshKeyDeleted: MutableLiveData<String> = MutableLiveData()
    var gpgKeyDeleted: MutableLiveData<String> = MutableLiveData()

    fun deleteSshKey(key: Long) = viewModelScope.launch {
        val res = repository.delSshKey(key)
        if (res.code() == 204) {
            sshKeyDeleted.value = "true"
        } else {
            Timber.d(res.message())
            sshKeyDeleted.value = res.message()
        }
    }

    fun deleteGpgKey(key: Long) = viewModelScope.launch {
        val res = repository.delGpgKey(key)
        if (res.code() == 204) {
            gpgKeyDeleted.value = "true"
        } else {
            Timber.d(res.message())
            gpgKeyDeleted.value = res.message()
        }
    }
}
