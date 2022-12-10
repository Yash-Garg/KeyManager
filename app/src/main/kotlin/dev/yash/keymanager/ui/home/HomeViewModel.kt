package dev.yash.keymanager.ui.home

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.yash.keymanager.data.api.GithubRepository
import dev.yash.keymanager.paging.GithubPagingSource
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val repository: GithubRepository,
    private val pagingSourceFactory: GithubPagingSource.Factory
) : ViewModel() {
    private val sshKeysPager =
        Pager(PagingConfig(pageSize = 10)) { pagingSourceFactory.create(repository::getSshKeys) }

    private val gpgKeysPager =
        Pager(PagingConfig(pageSize = 10)) { pagingSourceFactory.create(repository::getGpgKeys) }

    val sshKeys
        get() = sshKeysPager.flow

    val gpgKeys
        get() = gpgKeysPager.flow
}
