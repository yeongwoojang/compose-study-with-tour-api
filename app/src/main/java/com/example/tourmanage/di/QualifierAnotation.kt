package com.example.tourmanage.di

import javax.inject.Qualifier
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ServerOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ServerInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ServerRetrofit




