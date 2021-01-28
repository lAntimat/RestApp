package ru.lantimat.my.presentation

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

abstract class BaseVmFragment(@LayoutRes val contentLayoutId: Int) :
    Fragment(contentLayoutId) {

    fun <T> LiveData<T>.subscribe(subber: (T) -> Unit) {
        observe(viewLifecycleOwner, Observer { subber.invoke(it) })
    }
}