package dev.yash.keymanager.api

import dev.yash.keymanager.models.GpgKey
import dev.yash.keymanager.models.SshKey
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface GitHubService {
    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/user/keys")
    suspend fun getSshKeys(
        @Header("Authorization") token: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
    ): List<SshKey>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/user/gpg_keys")
    suspend fun getGpgKeys(
        @Header("Authorization") token: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
    ): List<GpgKey>

    companion object {
        const val BASE_URL = "https://api.github.com"
    }
}
