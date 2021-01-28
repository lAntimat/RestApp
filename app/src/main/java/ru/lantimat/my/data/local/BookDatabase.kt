package ru.lantimat.my.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.lantimat.my.data.local.dao.BookDao
import ru.lantimat.my.data.local.model.DbBook

@Database(
    entities = [
        DbBook::class,
    ], version = 1
)
abstract class BookDatabase : RoomDatabase() {

    //abstract val bookDao: BookDao

    companion object {

    }
}
