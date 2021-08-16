package dev.yash.keymanager.api

import dev.yash.keymanager.models.GpgKey
import dev.yash.keymanager.models.SshKey
import retrofit2.http.GET

interface GitHubService {
    @GET("user/keys")
    suspend fun getSshKeys(): List<SshKey>

    @GET("user/gpg_keys")
    suspend fun getGpgKeys(): List<GpgKey>

    companion object {
        const val BASE_URL = "https://api.github.com"
    }
}
