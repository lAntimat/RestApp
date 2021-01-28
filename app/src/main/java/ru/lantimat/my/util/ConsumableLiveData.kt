package ru.cometrica.arbook.util

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


open class ConsumableLiveData<T> : MutableLiveData<ConsumableValue<T>>()

fun <T> ConsumableLiveData<T>.setValue(value: T) {
    this.value = ConsumableValue(value)
}

fun <T> ConsumableLiveData<T>.postValue(value: T) {
    this.postValue(
        ConsumableValue(
            value
        )
    )
}

open class ConsumableObserver<T>(private val block: (T) -> Unit) : Observer<ConsumableValue<T>> {

    override fun onChanged(t: ConsumableValue<T>?) {
        t?.consume(block)
    }

}

open class ConsumableValue<T>(private val data: T) {

    private var consumed = false

    fun consume(block: (T) -> Unit) {
        if (!consumed) {
            consumed = true
            block(data)
        }
    }

}
