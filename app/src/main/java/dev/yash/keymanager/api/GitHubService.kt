package dev.yash.keymanager.api

import dev.yash.keymanager.models.GpgKey
import dev.yash.keymanager.models.GpgModel
import dev.yash.keymanager.models.SshKey
import dev.yash.keymanager.models.SshModel
import retrofit2.http.*

interface GitHubService {
    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/user/keys")
    suspend fun getSshKeys(
        @Header("Authorization") token: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): List<SshKey>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/user/gpg_keys")
    suspend fun getGpgKeys(
        @Header("Authorization") token: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): List<GpgKey>

    @Headers("Accept: application/vnd.github.v3+json")
    @POST("/user/keys")
    suspend fun postSshKey(
        @Header("Authorization") token: String,
        @Body key: SshModel
    )

    @Headers("Accept: application/vnd.github.v3+json")
    @DELETE("/user/keys/{keyID}")
    suspend fun deleteSshKey(
        @Header("Authorization") token: String,
        @Path(value = "keyID") keyID: Long
    )

    @Headers("Accept: application/vnd.github.v3+json")
    @POST("/user/gpg_keys")
    suspend fun postGpgKey(
        @Header("Authorization") token: String,
        @Body armoredKey: GpgModel
    )

    @Headers("Accept: application/vnd.github.v3+json")
    @DELETE("/user/gpg_keys/{keyID}")
    suspend fun deleteGpgKey(
        @Header("Authorization") token: String,
        @Path(value = "keyID") keyID: Long
    )

    companion object {
        const val BASE_URL = "https://api.github.com"
    }
}
