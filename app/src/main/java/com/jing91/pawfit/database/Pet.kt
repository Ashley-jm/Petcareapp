package com.jing91.pawfit.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pets")
data class Pet(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val birthday: String,
    val breed: String,
    val type: String
)
