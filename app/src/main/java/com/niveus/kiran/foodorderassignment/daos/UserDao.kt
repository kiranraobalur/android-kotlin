package com.niveus.kiran.foodorderassignment.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.niveus.kiran.foodorderassignment.entities.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Query("Select * from User where emailId=:email")
    fun getUser(email:String): LiveData<User>
}