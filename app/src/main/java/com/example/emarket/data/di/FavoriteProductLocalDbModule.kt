package com.example.emarket.data.di

import android.content.Context
import com.example.emarket.data.api.local.FavoriteProductDao
import com.example.emarket.data.api.local.FavoriteProductDatabase
import com.example.emarket.data.repository.FavoriteProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavoriteProductLocalDbModule {

    @Provides
    @Singleton
    fun provideFavoriteDatabase(context: Context): FavoriteProductDatabase {
        return FavoriteProductDatabase.getDatabase(context)
    }

    @Provides
    fun provideFavoriteProductDao(database: FavoriteProductDatabase): FavoriteProductDao {
        return database.favoriteProductDao()
    }

    @Provides
    @Singleton
    fun provideFavoriteRepository(favoriteProductDao: FavoriteProductDao): FavoriteProductRepository {
        return FavoriteProductRepository(favoriteProductDao)
    }
}
