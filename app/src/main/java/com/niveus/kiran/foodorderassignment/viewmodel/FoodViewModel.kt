package com.niveus.kiran.foodorderassignment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.niveus.kiran.foodorderassignment.daos.CartDao
import com.niveus.kiran.foodorderassignment.database.FoodDatabase
import com.niveus.kiran.foodorderassignment.entities.Cart
import com.niveus.kiran.foodorderassignment.entities.Food
import com.niveus.kiran.foodorderassignment.repositories.CartRepository
import com.niveus.kiran.foodorderassignment.repositories.FoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoodViewModel(application: Application) : AndroidViewModel(application) {

    val allFood:LiveData<List<Food>>
    val foodRepository: FoodRepository
    val cartRepository: CartRepository

    init {
        val fDao = FoodDatabase.getDatabase(application).getFoodDao()
        val cDao = FoodDatabase.getDatabase(application).getCartDao()
        foodRepository = FoodRepository(fDao)
        cartRepository = CartRepository(cDao)
        allFood = foodRepository.allFood
    }

    fun insert(food: Food) = viewModelScope.launch(Dispatchers.IO) {
        foodRepository.insert(food)
    }

    fun insert(cart: Cart) = viewModelScope.launch(Dispatchers.IO) {
        cartRepository.insert(cart)
    }

    fun delete(cart: Cart) = viewModelScope.launch(Dispatchers.IO) {
        cartRepository.delete(cart)
    }

    fun update(cart: Cart) = viewModelScope.launch(Dispatchers.IO) {
        cartRepository.update(cart)
    }

}