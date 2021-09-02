package dev.yash.keymanager.api

import android.content.SharedPreferences
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dev.yash.keymanager.models.GpgModel
import dev.yash.keymanager.models.SshModel
import javax.inject.Inject

@Module
@InstallIn(ActivityRetainedComponent::class)
class GithubRepository @Inject constructor(
    preferences: SharedPreferences,
    private val service: GitHubService
) {
    private val token = "token ${preferences.getString("ACCESS_TOKEN", null)}"
    suspend fun getSshKeys(page: Int, perPage: Int) = service.getSshKeys(token, perPage, page)
    suspend fun postSshKey(key: SshModel) = service.postSshKey(token, key)
    suspend fun getGpgKeys(page: Int, perPage: Int) = service.getGpgKeys(token, perPage, page)
    suspend fun postGpgKey(key: GpgModel) = service.postGpgKey(token, key)
}