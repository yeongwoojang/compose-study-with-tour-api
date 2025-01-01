package com.example.tourmanage.di.detail

import com.example.tourmanage.data.common.GetDetailCommonUseCase
import com.example.tourmanage.data.common.GetDetailImageUseCase
import com.example.tourmanage.data.common.GetDetailInfoUseCase
import com.example.tourmanage.data.common.GetDetailIntroUseCaseImpl
import com.example.tourmanage.domain.common.GetDetailCommonUseCaseImpl
import com.example.tourmanage.domain.common.GetDetailImageUseCaseImpl
import com.example.tourmanage.domain.common.GetDetailInfoUseCaseImpl
import com.example.tourmanage.domain.common.GetDetailIntroUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DetailDataModule {
    @Binds
    abstract fun bindGetDetailInfoUseCase(uc: GetDetailInfoUseCaseImpl): GetDetailInfoUseCase
    @Binds
    abstract fun bindGetDetailCommonInfoUseCase(uc: GetDetailCommonUseCaseImpl): GetDetailCommonUseCase
    @Binds
    abstract fun bindGetDetailImageInfoUseCase(uc: GetDetailImageUseCaseImpl): GetDetailImageUseCase
    @Binds
    abstract fun bindGetDetailIntroUseCase(uc: GetDetailIntroUseCaseImpl): GetDetailIntroUseCase

}