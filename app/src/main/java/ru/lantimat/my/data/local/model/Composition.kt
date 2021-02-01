package ru.lantimat.my.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "composition")
data class Composition(
    @PrimaryKey
    val id: Int,
    val name: String,
    val imgUrl: String
)