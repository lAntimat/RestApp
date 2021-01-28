package ru.lantimat.my.data

import retrofit2.http.GET

interface ApiService {

    @GET("")
    suspend fun getAllBooks(): CloudResponse<List<String>>

}
