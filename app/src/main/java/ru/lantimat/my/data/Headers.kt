package ru.lantimat.my.data

object Headers {

    const val AUTHORIZATION = "Authorization"

    fun makeAuthorizationHeaderValue(accessToken: String) = "Bearer $accessToken"
}