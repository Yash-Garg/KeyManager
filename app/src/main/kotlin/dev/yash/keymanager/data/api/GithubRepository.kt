package dev.yash.keymanager.data.api

import android.content.SharedPreferences
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dev.yash.keymanager.data.models.GpgKey
import dev.yash.keymanager.data.models.GpgModel
import dev.yash.keymanager.data.models.Key
import dev.yash.keymanager.data.models.KeyModel
import dev.yash.keymanager.data.models.SshKey
import dev.yash.keymanager.data.models.SshModel
import dev.yash.keymanager.data.utils.AuthConfig
import javax.inject.Inject
import okhttp3.ResponseBody
import retrofit2.Response

@Module
@InstallIn(ActivityRetainedComponent::class)
class GithubRepository
@Inject
constructor(preferences: SharedPreferences, private val api: GitHubApi) {
    private val token = "token ${preferences.getString(AuthConfig.TOKEN_KEY, null)}"

    suspend fun getGpgKeys(page: Int, perPage: Int): List<GpgKey> {
        return api.getGpgKeys(token, perPage, page)
    }

    suspend fun getSshKeys(page: Int, perPage: Int): List<SshKey> {
        return api.getSshKeys(token, perPage, page)
    }

    suspend fun getSshSigningKeys(page: Int, perPage: Int): List<SshKey> {
        return api.getSshSigningKeys(token, perPage, page)
    }

    suspend fun createKey(key: KeyModel) {
        return when (key) {
            is GpgModel -> api.createGpgKey(token, key)
            is SshModel -> api.createSshKey(token, key)
        }
    }

    suspend fun createSshSigningKey(key: SshModel) {
        return api.createSshSigningKey(token, key)
    }

    suspend fun deleteKey(key: Key): Response<ResponseBody> {
        return when (key) {
            is GpgKey -> api.deleteGpgKey(token, key.id)
            is SshKey -> api.deleteSshKey(token, key.id)
        }
    }

    suspend fun getSshKeyFromId(keyId: Long) = api.getSshKeyFromId(token, keyId)

    suspend fun getSshSigningKeyFromId(keyId: Long) = api.getSshSigningKeyfromId(token, keyId)

    suspend fun getGpgKeyFromId(keyId: Long) = api.getGpgKeyfromId(token, keyId)
}
