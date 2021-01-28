package ru.lantimat.my.data.models

import ru.lantimat.my.presentation.menulist.MenuAndHeader

data class MenuItem (
    val id: Int,
    val categoryId: Int,
    val name: String,
    val description: String,
    val price: Int,
    val imgUrl: String
): MenuAndHeader