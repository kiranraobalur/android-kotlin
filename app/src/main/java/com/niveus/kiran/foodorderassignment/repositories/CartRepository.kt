package com.niveus.kiran.foodorderassignment.repositories

import androidx.lifecycle.LiveData
import com.niveus.kiran.foodorderassignment.daos.CartDao
import com.niveus.kiran.foodorderassignment.daos.FoodDao
import com.niveus.kiran.foodorderassignment.entities.Cart
import com.niveus.kiran.foodorderassignment.entities.Food


class CartRepository(private val cartDao: CartDao) {

    val allCart: LiveData<List<Cart>> = cartDao.getAllCart()

    suspend fun insert(cart: Cart) {
        cartDao.insert(cart)
    }

    suspend fun delete(cart: Cart) {
        cartDao.delete(cart)
    }

    suspend fun update(cart: Cart) {
        cartDao.update(cart)
    }

    suspend fun nukeTable(){
        cartDao.nukeTable()
    }

}