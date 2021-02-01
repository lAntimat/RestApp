package ru.lantimat.my.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.lantimat.my.presentation.menulist.MenuAndHeader

@Entity(tableName = "menu_category")
data class MenuCategory (
    @PrimaryKey
    val id: Int,
    val name: String,
): MenuAndHeader