package com.niveus.kiran.foodorderassignment.repositories

import androidx.lifecycle.LiveData
import com.niveus.kiran.foodorderassignment.daos.FoodDao
import com.niveus.kiran.foodorderassignment.entities.Food

class FoodRepository(private val foodDao: FoodDao) {

    val allFood: LiveData<List<Food>> = foodDao.getAllFood()

    suspend fun insert(food: Food) {
        foodDao.insert(food)
    }

    /*suspend fun delete(food: Food) {
        foodDao.delete(food)
    }

    suspend fun update(food: Food) {
        foodDao.update(food)
    }*/

}