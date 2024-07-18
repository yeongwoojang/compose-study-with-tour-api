package com.example.tourmanage.common.di

import com.example.tourmanage.usecase.data.search.GetSearchUseCaseImpl
import com.example.tourmanage.usecase.domain.search.GetSearchUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchModule {
    @Binds
    abstract fun bindGetSearchUseCase(uc: GetSearchUseCaseImpl): GetSearchUseCase

}