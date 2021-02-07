package ru.lantimat.my.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "basket_dish")
data class BasketDishItem(
    @PrimaryKey
    val id: Int,
    val name: String,
    val imgUrl: String,
    var count: Int,
    val price: Float,
    var ingredients: String = "",
    var without: String = ""
)