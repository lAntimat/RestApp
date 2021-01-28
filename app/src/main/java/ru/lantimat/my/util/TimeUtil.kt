package ru.cometrica.arbook.util


object TimeUtil {

    fun msecToMmSs(msec: Int): String {
        val minutes = ((msec / 1000) / 60).toString()
        val seconds = (msec / 1000) % 60
        return "$minutes:${if (seconds < 10) "0$seconds" else seconds.toString()}"
    }

}