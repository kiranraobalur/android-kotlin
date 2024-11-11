package com.niveus.kiran.foodorderassignment.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Cart")
class Cart(
    @ColumnInfo(name = "fName")
    var fName: String,

    @ColumnInfo(name = "fCategory")
    var fCategory: String,

    @ColumnInfo(name = "fQuantity")
    var fQuantity: Int,

    @ColumnInfo(name = "fPrice")
    var fPrice: Double,

    @ColumnInfo(name = "amount")
    var amount: Double
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id = 0
}