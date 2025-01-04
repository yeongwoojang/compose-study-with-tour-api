package com.example.tourmanage.di.network

import android.content.Context
import com.example.tourmanage.common.repository.ServiceAPI
import com.example.tourmanage.di.ServerInterceptor
import com.example.tourmanage.di.ServerOkHttpClient
import com.example.tourmanage.di.ServerRetrofit
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
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
    @Singleton
    @Provides
    fun getInterceptor() = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    @ServerInterceptor
    fun getNetworkInterceptor() = Interceptor {
        val request = it.request()
        it.proceed(request)
    }


    @Singleton
    @Provides
    @ServerOkHttpClient
    fun getOkHttpClient(@ApplicationContext context: Context, loggingInterceptor: HttpLoggingInterceptor, @ServerInterceptor serverInterceptor: Interceptor)
    = OkHttpClient.Builder().apply {
        readTimeout(3, TimeUnit.SECONDS)
        connectTimeout(3, TimeUnit.SECONDS)
        writeTimeout(3, TimeUnit.SECONDS)
        addInterceptor(loggingInterceptor)
        addInterceptor(serverInterceptor)
    }.build()

    @Singleton
    @Provides
    @ServerRetrofit
    fun getClient(@ServerOkHttpClient client: OkHttpClient): Retrofit = Retrofit.Builder()
            .baseUrl(ServiceAPI.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()))
            .client(client)
            .build()

    @Singleton
    @Provides
    fun getServerService(@ServerRetrofit retrofit: Retrofit): ServiceAPI =
        retrofit.create(ServiceAPI::class.java)
}