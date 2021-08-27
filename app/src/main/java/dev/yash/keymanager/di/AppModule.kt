package dev.yash.keymanager.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.yash.keymanager.api.GitHubService
import dev.zacsweers.moshix.reflect.MetadataKotlinJsonAdapterFactory
import net.openid.appauth.AuthorizationService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder().baseUrl(GitHubService.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client).build()

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().add(MetadataKotlinJsonAdapterFactory()).build()

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): GitHubService = retrofit.create(GitHubService::class.java)

    @Singleton
    @Provides
    fun provideEncryptedSharedPrefs(@ApplicationContext context: Context): SharedPreferences {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        return EncryptedSharedPreferences.create(
            "token_encrypted_prefs",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Provides
    fun provideAuthService(@ApplicationContext context: Context) = AuthorizationService(context)
}
