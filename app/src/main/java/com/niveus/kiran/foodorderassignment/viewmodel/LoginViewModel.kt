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

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    var listUser : LiveData<User>? = null
    val uRepository : UserRepository


    init {
        val uDao = FoodDatabase.getDatabase(application).getUserDao()
        uRepository = UserRepository(uDao)
        //listUser = uRepository.get(email)
    }

    fun insertUser(user: User) =  viewModelScope.launch(Dispatchers.IO) {
        uRepository.insert(user)
    }

    fun get(email:String) =  viewModelScope.launch(Dispatchers.IO) {
        listUser= uRepository.get(email)
    }
}