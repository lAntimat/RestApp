package ru.lantimat.my.presentation.menulist.models

data class MenuCategoryUi(
    val id: Int,
    val name: String,
    var isChecked: Boolean = false
)