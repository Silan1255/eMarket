package com.example.emarket.data.repository

import androidx.lifecycle.LiveData
import com.example.emarket.data.api.local.ProductDao
import com.example.emarket.data.model.CartProductList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepository @Inject constructor(private val productDao: ProductDao) {
    val allProducts: LiveData<List<CartProductList>> = productDao.getAllProducts()

    suspend fun insert(product: CartProductList) {
        productDao.insert(product)
    }

    suspend fun delete(product: CartProductList) {
        productDao.delete(product)
    }

    suspend fun update(product: CartProductList) {
        productDao.update(product)
    }

    suspend fun getProductByName(name: String): CartProductList? {
        return productDao.getProductByName(name)
    }
}

