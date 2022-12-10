package dev.yash.keymanager.data.api

import dev.yash.keymanager.data.models.GpgKey
import dev.yash.keymanager.data.models.GpgModel
import dev.yash.keymanager.data.models.SshKey
import dev.yash.keymanager.data.models.SshModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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
    suspend fun postSshKey(@Header("Authorization") token: String, @Body key: SshModel)

    @Headers("Accept: application/vnd.github.v3+json")
    @DELETE("/user/keys/{keyID}")
    suspend fun deleteSshKey(
        @Header("Authorization") token: String,
        @Path(value = "keyID") keyID: Long
    ): Response<ResponseBody>

    @Headers("Accept: application/vnd.github.v3+json")
    @POST("/user/gpg_keys")
    suspend fun postGpgKey(@Header("Authorization") token: String, @Body armoredKey: GpgModel)

    @Headers("Accept: application/vnd.github.v3+json")
    @DELETE("/user/gpg_keys/{keyID}")
    suspend fun deleteGpgKey(
        @Header("Authorization") token: String,
        @Path(value = "keyID") keyID: Long
    ): Response<ResponseBody>

    companion object {
        const val BASE_URL = "https://api.github.com"
    }
}
