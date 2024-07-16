package com.example.emarket.data.repository

import androidx.lifecycle.LiveData
import com.example.emarket.data.api.local.FavoriteProductDao
import com.example.emarket.data.model.FavoriteProductList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteProductRepository @Inject constructor(private val favoriteProductDao: FavoriteProductDao) {
    val allProducts: LiveData<List<FavoriteProductList>> = favoriteProductDao.getAllProducts()

    suspend fun insert(product: FavoriteProductList) {
        favoriteProductDao.insert(product)
    }

    suspend fun delete(product: FavoriteProductList) {
        favoriteProductDao.delete(product)
    }

}

