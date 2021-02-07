package ru.lantimat.my.presentation.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class IngredientUi(
    val id: Int,
    val name: String,
    val volume: String,
    val price: Int,
    val box: Int,
    val compositionId: Int,
    var isChecked: Boolean
)