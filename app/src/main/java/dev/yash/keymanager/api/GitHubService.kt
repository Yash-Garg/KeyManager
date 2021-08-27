package dev.yash.keymanager.api

import dev.yash.keymanager.models.GpgKey
import dev.yash.keymanager.models.SshKey
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface GitHubService {
    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/user/keys")
    fun getSshKeys(
        @Header("Authorization") token: String
    ): Call<List<SshKey>>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/user/gpg_keys")
    fun getGpgKeys(
        @Header("Authorization") token: String
    ): Call<List<GpgKey>>

    companion object {
        const val BASE_URL = "https://api.github.com"
    }
}
