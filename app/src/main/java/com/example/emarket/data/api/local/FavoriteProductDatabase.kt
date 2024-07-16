package com.example.emarket.data.api.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.emarket.data.model.FavoriteProductList

@Database(entities = [FavoriteProductList::class], version = 1, exportSchema = false)
abstract class FavoriteProductDatabase : RoomDatabase() {
    abstract fun favoriteProductDao() : FavoriteProductDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteProductDatabase? = null

        fun getDatabase(context: Context): FavoriteProductDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteProductDatabase::class.java,
                    "favorite_product_database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
