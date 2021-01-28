package ru.cometrica.arbook.util

import android.text.TextUtils

fun String?.isNumber() = !TextUtils.isEmpty(this) && TextUtils.isDigitsOnly(this)

fun String?.isNotNumber() = !isNumber()