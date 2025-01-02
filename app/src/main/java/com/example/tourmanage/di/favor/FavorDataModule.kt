package com.example.tourmanage.di.favor

import com.example.tourmanage.domain.favor.AddFavorUseCase
import com.example.tourmanage.domain.favor.DelFavorUseCase
import com.example.tourmanage.domain.favor.FavorRepository
import com.example.tourmanage.domain.favor.GetAllFavorUseCase
import com.example.tourmanage.domain.favor.GetFavorUseCase
import com.example.tourmanage.domain.favor.IsFavorUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class FavorDataModule {
    @Provides
    @ViewModelScoped
    fun getAddFavorUseCase(
        repository: FavorRepository
    ): AddFavorUseCase {
        return AddFavorUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun delAddFavorUseCase(
        repository: FavorRepository
    ): DelFavorUseCase {
        return DelFavorUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun getAllFavorUseCase(
        repository: FavorRepository
    ): GetAllFavorUseCase {
        return GetAllFavorUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun getFavorUseCase(
        repository: FavorRepository
    ): GetFavorUseCase {
        return GetFavorUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun isFavorUseCase(
        repository: FavorRepository
    ): IsFavorUseCase {
        return IsFavorUseCase(repository)
    }

}