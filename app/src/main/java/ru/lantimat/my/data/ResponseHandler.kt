package ru.lantimat.my.data

import android.content.res.Resources
import retrofit2.HttpException
import ru.cometrica.arbook.data.Response
import ru.lantimat.my.R
import java.net.SocketTimeoutException
import java.net.UnknownHostException


enum class ErrorCodes(val code: Int) {
    SocketTimeOut(1000),
    ServerUnreachable(1001)
}

open class ResponseHandler(val resources: Resources) {

    fun <T : Any> handleSuccess(data: T): Response<T> =
        Response.success(data)

    fun <T : Any> handleException(e: Exception): Response<T> =
        when (e) {
            is HttpException -> Response.error(getErrorMessage(e.code()), null)
            is UnknownHostException -> Response.error(
                getErrorMessage(ErrorCodes.ServerUnreachable.code),
                null
            )
            is SocketTimeoutException -> Response.error(
                getErrorMessage(ErrorCodes.SocketTimeOut.code),
                null
            )
            else -> Response.error(getErrorMessage(Int.MAX_VALUE), null)
        }

    private fun getErrorMessage(code: Int): String =
        when (code) {
            ErrorCodes.SocketTimeOut.code -> "Timeout"
            ErrorCodes.ServerUnreachable.code -> "Server not responding"
            else -> "General server problem"
        }

}
