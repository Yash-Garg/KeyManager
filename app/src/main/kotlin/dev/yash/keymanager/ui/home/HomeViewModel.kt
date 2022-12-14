package dev.yash.keymanager.ui.home

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.runCatching
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.yash.keymanager.data.api.GithubRepository
import dev.yash.keymanager.data.models.GpgModel
import dev.yash.keymanager.data.models.KeyActionEvent
import dev.yash.keymanager.data.models.KeyEvent
import dev.yash.keymanager.data.models.KeyModel
import dev.yash.keymanager.data.models.KeyType
import dev.yash.keymanager.data.models.SshModel
import dev.yash.keymanager.data.utils.AuthConfig
import dev.yash.keymanager.data.utils.ExceptionHandler
import dev.yash.keymanager.paging.GithubPagingSource
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val repository: GithubRepository,
    private val preferences: SharedPreferences,
    private val pagingSourceFactory: GithubPagingSource.Factory
) : ViewModel() {
    private val _status = MutableSharedFlow<KeyActionEvent>()
    val status = _status.asSharedFlow()

    private val sshKeysPager =
        Pager(PagingConfig(pageSize = 5)) { pagingSourceFactory.create(repository::getSshKeys) }

    private val sshSigningKeysPager =
        Pager(PagingConfig(pageSize = 5)) {
            pagingSourceFactory.create(repository::getSshSigningKeys)
        }

    private val gpgKeysPager =
        Pager(PagingConfig(pageSize = 5)) { pagingSourceFactory.create(repository::getGpgKeys) }

    val sshKeys
        get() = sshKeysPager.flow

    val sshSigningKeys
        get() = sshSigningKeysPager.flow

    val gpgKeys
        get() = gpgKeysPager.flow

    fun createKey(key: KeyModel) {
        viewModelScope.launch {
            val type = if (key is GpgModel) KeyType.GPG else KeyType.SSH
            when (val result = runCatching { repository.createKey(key) }) {
                is Ok ->
                    _status.emit(KeyActionEvent(type, KeyEvent.ADDED, "Successfully added key."))
                is Err ->
                    _status.emit(
                        KeyActionEvent(
                            type,
                            KeyEvent.FAILED,
                            ExceptionHandler.mapException(result.error.message)
                        )
                    )
            }
        }
    }

    fun createSigningKey(key: SshModel) {
        viewModelScope.launch {
            when (val result = runCatching { repository.createSshSigningKey(key) }) {
                is Ok ->
                    _status.emit(
                        KeyActionEvent(
                            KeyType.SSH_SIGNING,
                            KeyEvent.ADDED,
                            "Successfully added key."
                        )
                    )
                is Err ->
                    _status.emit(
                        KeyActionEvent(
                            KeyType.SSH_SIGNING,
                            KeyEvent.FAILED,
                            ExceptionHandler.mapException(result.error.message)
                        )
                    )
            }
        }
    }

    fun logout() = preferences.edit().remove(AuthConfig.TOKEN_KEY).apply()
}
