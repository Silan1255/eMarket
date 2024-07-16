package com.example.emarket.data.api.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.emarket.data.model.CartProductList

@Dao
interface ProductDao {
    @Query("SELECT * FROM products WHERE name = :name LIMIT 1")
    suspend fun getProductByName(name: String): CartProductList?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: CartProductList)

    @Delete
    suspend fun delete(product: CartProductList)

    @Update
    suspend fun update(product: CartProductList)

    @Query("SELECT * FROM products")
    fun getAllProducts(): LiveData<List<CartProductList>>
}
