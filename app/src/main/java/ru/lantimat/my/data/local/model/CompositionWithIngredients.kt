package ru.lantimat.my.data.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

data class CompositionWithIngredients(
    @Embedded
    val composition: Composition,
    @Relation(parentColumn = "id", entityColumn = "compositionId")
    val ingredients: List<Ingredient>
)