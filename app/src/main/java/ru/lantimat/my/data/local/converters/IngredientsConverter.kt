package ru.lantimat.my.data.local.converters

import androidx.room.TypeConverter
import java.lang.StringBuilder
import java.util.*
import java.util.stream.Collectors

class IngredientsConverter {
    @TypeConverter
    fun fromIngredients(ingredients: List<String?>): String? {
        val stringBuilder = StringBuilder()
        ingredients.forEach {
            stringBuilder.append(it)
            stringBuilder.append(",")
        }
        return stringBuilder.toString()
    }

    @TypeConverter
    fun toIngredients(data: String): MutableList<String?> {
        return data.split(",").toMutableList()
    }
}