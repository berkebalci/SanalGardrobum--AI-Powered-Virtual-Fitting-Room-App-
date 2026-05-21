package com.example.sanalgardrobum.data.local.di

import android.content.Context
import androidx.room.Room
import com.example.sanalgardrobum.data.local.AppDatabase
import com.example.sanalgardrobum.data.local.dao.GarmentDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "sanalgardrobum.db"
        ).build()
    }

    @Provides
    fun provideGarmentDao(database: AppDatabase): GarmentDao {
        return database.garmentDao()
    }
}
