package ru.lantimat.my.presentation.detailinfo

sealed class BottomPanelState {
    object Buy: BottomPanelState()
    object Counter: BottomPanelState()
}