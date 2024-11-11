package com.niveus.kiran.foodorderassignment.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
class User (
    @ColumnInfo(name = "userName")
    var userName: String,

    @ColumnInfo(name = "emailId")
    var emailId: String,

    @ColumnInfo(name = "password")
    var password: String

    )
    {
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id = 0
    }