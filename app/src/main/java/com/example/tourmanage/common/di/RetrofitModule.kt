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
    @Provides
    @Singleton
    fun getInterceptor() = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun getNetworkInterceptor() = Interceptor {
        Timber.d("Intercept")

        var replyCount = 0
        val request = it.request()
        var response: Response? = null
        var isSuccess = false
        while(replyCount < 2) { //_ 여기서 retry는 1회 시도
            try {
                Timber.e("Intercept() | proceed")
                response = it.proceed(request)
                isSuccess = response.body?.contentType().toString() == "application/json"
                if (isSuccess) {
                   break
                }
            } catch (e: Exception) {
                Timber.e("Intercept() | error: $e")
            }
            replyCount++
        }

        if (isSuccess) {
            val cacheControl = CacheControl.Builder()
                .maxAge(1, TimeUnit.HOURS)
                .build()

            response!!.newBuilder()
                .removeHeader("Cache-Control")
                .addHeader("Cache-Control", cacheControl.toString())
                .build()
        } else {
            Timber.e("Intercept() | last proceed")
            it.proceed(request)//_ 최종적으로 retry 한번 더 시도 (총 3회 조회까지 가능)
        }
    }

    @Provides
    @Singleton
    fun getOkHttpClient(@ApplicationContext context: Context, loggingIntercepter: HttpLoggingInterceptor, networkInterceptor: Interceptor)
    = OkHttpClient.Builder().apply {
//        cache(Cache(context.cacheDir, 1024 * 1024 * 10))
        readTimeout(100, TimeUnit.MILLISECONDS)
        connectTimeout(1, TimeUnit.SECONDS)
        writeTimeout(1, TimeUnit.SECONDS)
        addInterceptor(loggingIntercepter)
        addInterceptor(networkInterceptor)
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