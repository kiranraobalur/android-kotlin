package com.niveus.kiran.foodorderassignment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.niveus.kiran.foodorderassignment.daos.CartDao
import com.niveus.kiran.foodorderassignment.daos.FoodDao
import com.niveus.kiran.foodorderassignment.daos.UserDao
import com.niveus.kiran.foodorderassignment.entities.Cart
import com.niveus.kiran.foodorderassignment.entities.Food
import com.niveus.kiran.foodorderassignment.entities.User


@Database(entities = [User::class,Food::class,Cart::class], version = 1, exportSchema = false)
abstract class FoodDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getFoodDao(): FoodDao
    abstract fun getCartDao(): CartDao

    companion object {

        @Volatile
        private var INSTANCE: FoodDatabase? = null

        fun getDatabase(context: Context): FoodDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FoodDatabase::class.java,
                    "note_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}