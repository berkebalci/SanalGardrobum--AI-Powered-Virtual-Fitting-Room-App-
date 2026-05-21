package com.example.sanalgardrobum.data.di

import com.example.sanalgardrobum.data.repository.AuthRepositoryImpl
import com.example.sanalgardrobum.data.repository.GarmentRepositoryImpl
import com.example.sanalgardrobum.domain.repository.AuthRepository
import com.example.sanalgardrobum.domain.repository.GarmentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindGarmentRepository(impl: GarmentRepositoryImpl): GarmentRepository
}
