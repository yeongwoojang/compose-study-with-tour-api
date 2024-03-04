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
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
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
    fun getOkHttpClient(@ApplicationContext context: Context, interceptor: HttpLoggingInterceptor)
    = OkHttpClient.Builder().apply {
        interceptors().add(interceptor)
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