package com.example.emarket.data.di

import android.app.Application
import android.content.Context
import com.example.emarket.data.api.local.ProductDao
import com.example.emarket.data.api.local.ProductDatabase
import com.example.emarket.data.repository.CartRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDatabaseModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context): ProductDatabase {
        return ProductDatabase.getDatabase(context)
    }

    @Provides
    fun provideProductDao(database: ProductDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    @Singleton
    fun provideCartRepository(productDao: ProductDao): CartRepository {
        return CartRepository(productDao)
    }
}
