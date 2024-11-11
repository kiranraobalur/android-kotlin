package com.niveus.kiran.foodorderassignment.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Food")
class Food(
    @ColumnInfo(name = "fName")
    var fName:String,

    @ColumnInfo(name = "fCategory")
    var fCategory:String,

    @ColumnInfo(name = "fPrice")
    var fPrice:Double
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id = 0
}