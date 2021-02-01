package ru.lantimat.my.data.local.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.Relation
import ru.lantimat.my.presentation.menulist.MenuAndHeader

@Entity(tableName = "menu_item")
data class MenuItem (
    @PrimaryKey
    val id: Int,
    val categoryId: Int,
    val name: String,
    val description: String,
    val price: Float,
    val imgUrl: String,
    var count: Int = 0,
    val descriptionStop: String = "",
    val idPattern1: Int = 1,
    val idPattern2: Int = 1,
): MenuAndHeader