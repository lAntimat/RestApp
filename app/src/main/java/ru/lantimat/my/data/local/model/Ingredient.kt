package ru.lantimat.my.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredient")
data class Ingredient(
    @PrimaryKey
    val id: Int,
    val name: String,
    val volume: String,
    val price: Int,
    val box: Int,
    val compositionId: Int
)