package com.example.emarket.data.api.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.emarket.data.model.FavoriteProductList

@Dao
interface FavoriteProductDao {

    @Query("SELECT * FROM favorite_products WHERE name = :name LIMIT 1")
    suspend fun getProductByName(name: String): FavoriteProductList?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: FavoriteProductList)

    @Delete
    suspend fun delete(product: FavoriteProductList)

    @Update
    suspend fun update(product: FavoriteProductList)

    @Query("SELECT * FROM favorite_products")
    fun getAllProducts(): LiveData<List<FavoriteProductList>>

}
