package com.example.tourmanage.common.di

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FireBaseModule @Inject constructor() {

    @Provides
    @Singleton
    fun getFirebaseClient(): FirebaseDatabase = Firebase.database

    @Provides
    fun getReference(ref: String): DatabaseReference = getFirebaseClient().getReference(ref)
}