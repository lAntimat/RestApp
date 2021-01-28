package ru.cometrica.arbook.util


import okio.*
import java.io.File
import java.io.InputStream

private const val BUFFER_SIZE = 8192

fun File.sinkAll(source: BufferedSource) {
    sink(false).use { sink ->
        val buffer = Buffer()
        var bytesRead: Long
        do {
            bytesRead = source.read(buffer, BUFFER_SIZE.toLong())
            if (bytesRead > 0) {
                sink.write(buffer, bytesRead)
            }
        } while (bytesRead > 0)
        sink.flush()
    }
}

fun File.sinkAll(source: InputStream) {
    source.use { stream ->
        sinkAll(stream.source().buffer())
    }
}