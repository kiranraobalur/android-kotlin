package com.niveus.kiran.foodorderassignment.repositories

import androidx.lifecycle.LiveData
import com.niveus.kiran.foodorderassignment.daos.UserDao
import com.niveus.kiran.foodorderassignment.entities.User

class UserRepository(private val userDao: UserDao) {



    suspend fun get(email: String):LiveData<User> {
        val userlogin: LiveData<User> = userDao.getUser(email)
        return userlogin
    }

    suspend fun insert(user: User) {
        userDao.insert(user)
    }
}