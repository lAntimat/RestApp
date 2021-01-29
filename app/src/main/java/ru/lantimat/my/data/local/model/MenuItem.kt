package ru.lantimat.my.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.lantimat.my.presentation.menulist.MenuAndHeader

@Entity(tableName = "menu_item")
data class MenuItem (
    @PrimaryKey
    val id: Int,
    val categoryId: Int,
    val name: String,
    val description: String,
    val price: Int,
    val imgUrl: String
): MenuAndHeader