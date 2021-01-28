package ru.lantimat.my.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book")
data class DbBook(

    @PrimaryKey
    val id: String = "",

    val title: String = "",

    val author: String = "",

    val annotation: String = "",

    val qrCode: String = "",

    val isbn: String = "",

    val cover: String = "",

    val localVersion: String = "",

    val remoteVersion: String = "",

    val favorite: Boolean = false,

    val androidImageDb: String? = "",

    val size : Int = 0
)
