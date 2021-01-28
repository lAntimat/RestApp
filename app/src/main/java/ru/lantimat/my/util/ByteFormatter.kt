package ru.cometrica.arbook.util

import java.text.CharacterIterator
import java.text.StringCharacterIterator

object ByteFormatter {

    fun humanReadableByteCountSI(sizeInBytes: Long): String {
        var bytes = sizeInBytes
        if (-1000 < bytes && bytes < 1000) {
            return "$bytes B"
        }
        val ci: CharacterIterator = StringCharacterIterator("kMGTPE")
        while (bytes <= -999950 || bytes >= 999950) {
            bytes /= 1000
            ci.next()
        }
        return "%.1f %cB".format(bytes / 1000.0, ci.current())
    }

    fun formatToMb(bytes: Long): String {
        return "%.2f".format(bytes / 1024 / 1024f)
    }
}