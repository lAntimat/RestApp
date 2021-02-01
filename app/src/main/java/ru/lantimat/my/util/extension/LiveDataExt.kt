package ru.lantimat.my.util.extension

import androidx.lifecycle.MutableLiveData

fun MutableLiveData<*>.refresh() {
    this.value = this.value
}