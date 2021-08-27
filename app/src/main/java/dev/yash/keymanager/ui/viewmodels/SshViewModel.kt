package dev.yash.keymanager.ui.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.yash.keymanager.api.GithubRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class SshViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {
    val sshKeys = flow {
        emit(repository.getSshKeys())
    }

    val gpgKeys = flow {
        emit(repository.getGpgKeys())
    }
}
