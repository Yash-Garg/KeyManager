package dev.yash.keymanager.api

import android.content.SharedPreferences
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import javax.inject.Inject

@Module
@InstallIn(ActivityRetainedComponent::class)
class GithubRepository @Inject constructor(
    preferences: SharedPreferences,
    private val service: GitHubService
) {
    private val token = preferences.getString("ACCESS_TOKEN", null)
    suspend fun getSshKeys() = service.getSshKeys("token $token")
    suspend fun getGpgKeys() = service.getGpgKeys("token $token")
}
