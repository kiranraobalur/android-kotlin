package com.niveus.kiran.foodorderassignment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.niveus.kiran.foodorderassignment.database.FoodDatabase
import com.niveus.kiran.foodorderassignment.entities.User
import com.niveus.kiran.foodorderassignment.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    //val listUser : LiveData<List<User>>
    val uRepository: UserRepository

    init {
        val uDao = FoodDatabase.getDatabase(application).getUserDao()
        uRepository = UserRepository(uDao)
    }

    fun insertUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        uRepository.insert(user)
    }

}