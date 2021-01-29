package ru.lantimat.my.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "basket_dish")
data class BasketDishItem(
    @PrimaryKey
    val id: Int,
    val count: Int
)