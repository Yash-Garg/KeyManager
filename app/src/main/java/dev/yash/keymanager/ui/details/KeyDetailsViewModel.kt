package dev.yash.keymanager.ui.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.yash.keymanager.api.GithubRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KeyDetailsViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {
    var sshKeyDeleted: MutableLiveData<Boolean> = MutableLiveData()
    var gpgKeyDeleted: MutableLiveData<Boolean> = MutableLiveData()

    fun deleteSshKey(key: Long) = viewModelScope.launch {
        try {
            repository.delSshKey(key)
            sshKeyDeleted.value = true
        } catch (e: Exception) {
            Log.e("ERROR", e.toString())
        }
    }

    fun deleteGpgKey(key: Long) = viewModelScope.launch {
        try {
            repository.delGpgKey(key)
            gpgKeyDeleted.value = true
        } catch (e: Exception) {
            Log.e("ERROR", e.toString())
        }
    }
}
