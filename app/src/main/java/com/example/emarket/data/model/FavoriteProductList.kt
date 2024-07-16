package com.example.emarket.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_products")
data class FavoriteProductList(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val price: String,
    val image: String,
)