package ru.cometrica.arbook.util

fun <T> List<T>.replace(newValue: T, predicate: (T) -> Boolean) =
    map { if (predicate(it)) newValue else it }