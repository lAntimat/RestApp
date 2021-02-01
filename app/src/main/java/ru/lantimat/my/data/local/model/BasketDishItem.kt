package ru.lantimat.my.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import ru.lantimat.my.data.local.converters.IngredientsConverter

@Entity(tableName = "basket_dish")
@TypeConverters(IngredientsConverter::class)
data class BasketDishItem(
    @PrimaryKey
    val id: Int,
    val name: String,
    val imgUrl: String,
    var count: Int,
    val price: Float,
    val ingredients: List<String> = listOf(),
    val without: List<String> = listOf()
)