package com.example.tourmanage.common.di

import android.content.Context
import com.example.tourmanage.common.repository.ServiceAPI
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * 네트워크 모듈
 */
@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {
    @Provides
    @Singleton
    fun getInterceptor() = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun getNetworkInterceptor() = Interceptor {
        Timber.d("OkHttp Intercept")
        val request = it.request()
        val response = it.proceed(request)
        val cacheControl = CacheControl.Builder()
            .maxAge(1, TimeUnit.HOURS)
            .build()
        response.newBuilder()
            .removeHeader("Cache-Control")
            .addHeader("Cache-Control", cacheControl.toString())
            .build()
    }

    @Provides
    @Singleton
    fun getOkHttpClient(@ApplicationContext context: Context, loggingIntercepter: HttpLoggingInterceptor, networkInterceptor: Interceptor)
    = OkHttpClient.Builder().apply {
//        cache(Cache(context.cacheDir, 1024 * 1024 * 10))
        addInterceptor(loggingIntercepter)
        addNetworkInterceptor(networkInterceptor)
    }.build()

    @Provides
    @Singleton
    fun getClient(client: OkHttpClient): ServiceAPI = Retrofit.Builder()
            .baseUrl(ServiceAPI.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()))
            .client(client)
            .build().create(ServiceAPI::class.java)

}