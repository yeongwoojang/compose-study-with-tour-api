package com.example.tourmanage.common.di

import android.content.Context
import com.example.tourmanage.common.repository.SearchAPI
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
    @Singleton
    @Provides
    fun getInterceptor() = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    @ServerInterceptor
    fun getNetworkInterceptor() = Interceptor {
        Timber.d("Intercept")

        var replyCount = 0
        val request = it.request()
        var response: Response? = null
        var isSuccess = false
        response = it.proceed(request)

        response
//        while(replyCount < 2) { //_ 여기서 retry는 1회 시도
//            try {
//                Timber.e("Intercept() | proceed")
//                response = it.proceed(request)
//                isSuccess = response.body?.contentType().toString() == "application/json"
//                if (isSuccess) {
//                   break
//                }
//            } catch (e: Exception) {
//                Timber.e("Intercept() | error: $e")
//            }
//            replyCount++
//        }
//
//        if (isSuccess) {
//            val cacheControl = CacheControl.Builder()
//                .maxAge(1, TimeUnit.HOURS)
//                .build()
//
//            response!!.newBuilder()
//                .removeHeader("Cache-Control")
//                .addHeader("Cache-Control", cacheControl.toString())
//                .build()
//        } else {
//            Timber.e("Intercept() | last proceed")
//            it.proceed(request)//_ 최종적으로 retry 한번 더 시도 (총 3회 조회까지 가능)
//        }
    }

    @Singleton
    @Provides
    @SearchInterceptor
    fun getSearchInterceptor() = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("X-Naver-Client-Id", SearchAPI.CLIENT_ID)
            .addHeader("X-Naver-Client-Secret", SearchAPI.SECRET_KEY)
            .build()
        chain.proceed(request)
    }

    @Singleton
    @Provides
    @ServerOkHttpClient
    fun getOkHttpClient(@ApplicationContext context: Context, loggingInterceptor: HttpLoggingInterceptor, @ServerInterceptor serverInterceptor: Interceptor)
    = OkHttpClient.Builder().apply {
//        cache(Cache(context.cacheDir, 1024 * 1024 * 10))
        readTimeout(3, TimeUnit.SECONDS)
        connectTimeout(3, TimeUnit.SECONDS)
        writeTimeout(3, TimeUnit.SECONDS)
        addInterceptor(loggingInterceptor)
        addInterceptor(serverInterceptor)
    }.build()

    @Singleton
    @Provides
    @SearchOkHttpClient
    fun getSearchOkHttpClient(@ApplicationContext context: Context, loggingIntercepter: HttpLoggingInterceptor, @SearchInterceptor searchInterceptor: Interceptor)
            = OkHttpClient.Builder().apply {
//        cache(Cache(context.cacheDir, 1024 * 1024 * 10))
        readTimeout(3, TimeUnit.SECONDS)
        connectTimeout(3, TimeUnit.SECONDS)
        writeTimeout(3, TimeUnit.SECONDS)
        addInterceptor(loggingIntercepter)
        addInterceptor(searchInterceptor)
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
    @SearchRetrofit
    fun getSearchClient(@SearchOkHttpClient client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(SearchAPI.SEARCH_URL)
        .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()))
        .client(client)
        .build()

    @Singleton
    @Provides
    fun getServerService(@ServerRetrofit retrofit: Retrofit): ServiceAPI =
        retrofit.create(ServiceAPI::class.java)

    @Singleton
    @Provides
    fun getSearchService(@SearchRetrofit retrofit: Retrofit): SearchAPI =
        retrofit.create(SearchAPI::class.java)
}