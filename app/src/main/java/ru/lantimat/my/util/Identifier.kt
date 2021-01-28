package ru.cometrica.arbook.util

import java.util.*

object Identifier {

    fun make(): String = UUID.randomUUID().toString()
}