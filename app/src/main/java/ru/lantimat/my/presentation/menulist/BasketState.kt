package ru.lantimat.my.presentation.menulist

sealed class BasketState {
    object Gone: BasketState()
    data class Visible(val sum: String): BasketState()
}