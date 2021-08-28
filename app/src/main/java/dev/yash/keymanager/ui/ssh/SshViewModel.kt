package dev.yash.keymanager.ui.ssh

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.yash.keymanager.api.GithubRepository
import dev.yash.keymanager.models.SshModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SshViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {
    var keyPosted: MutableLiveData<Boolean> = MutableLiveData()

    val sshKeys = flow {
        emit(repository.getSshKeys())
    }

    val gpgKeys = flow {
        emit(repository.getGpgKeys())
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
