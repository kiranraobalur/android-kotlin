package com.niveus.kiran.foodorderassignment.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.niveus.kiran.foodorderassignment.entities.Cart

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cart: Cart)

    @Delete
    suspend fun delete(cart: Cart)

    @Update
    suspend fun update(cart: Cart)

    @Query("Select * from Cart order by id ASC")
    fun getAllCart(): LiveData<List<Cart>>

    @Query("DELETE FROM Cart")
    fun nukeTable()
}