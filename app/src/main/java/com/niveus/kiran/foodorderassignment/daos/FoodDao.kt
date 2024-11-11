package com.niveus.kiran.foodorderassignment.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.niveus.kiran.foodorderassignment.entities.Food

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(food: Food)

    @Query("Select * from food order by id ASC")
    fun getAllFood(): LiveData<List<Food>>
}