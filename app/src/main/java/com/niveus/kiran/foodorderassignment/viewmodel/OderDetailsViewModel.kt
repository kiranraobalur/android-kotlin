package com.niveus.kiran.foodorderassignment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niveus.kiran.foodorderassignment.database.FoodDatabase
import com.niveus.kiran.foodorderassignment.entities.Cart
import com.niveus.kiran.foodorderassignment.repositories.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class OderDetailsViewModel(application: Application): ViewModel() {

    val allCart: LiveData<List<Cart>>
    val cartRepository: CartRepository

    init {

        val cDao = FoodDatabase.getDatabase(application).getCartDao()
        cartRepository = CartRepository(cDao)
        allCart = cartRepository.allCart
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
    fun nukeTable() = GlobalScope.launch(Dispatchers.IO) {
        cartRepository.nukeTable()
    }
}