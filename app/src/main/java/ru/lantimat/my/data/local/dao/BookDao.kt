package ru.lantimat.my.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.lantimat.my.data.local.model.DbBook

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(book: DbBook)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(book: List<DbBook>)

    @Query("DELETE FROM book WHERE id = :id")
    fun delete(id: String)

    @Query("DELETE FROM book WHERE id not in (:actualBookIds)")
    fun  deleteOldBooks(actualBookIds: List<String>)

    @Query("SELECT * FROM book WHERE id = :id LIMIT 1")
    fun find(id: String): DbBook?

    @Query("SELECT * FROM book")
    fun findAll(): List<DbBook>

    @Query("SELECT * FROM book WHERE UPPER(title) LIKE '%' || UPPER(:title) || '%'")
    fun findByText(title: String): List<DbBook>

    @Query("UPDATE book SET favorite =:favorite WHERE id = :bookId")
    fun changeFavorite(bookId: String, favorite: Boolean)

    @Query("UPDATE book SET state = :state WHERE id = :bookId")
    fun changeState(bookId: String, state: String)

    @Query("UPDATE book SET localVersion = :localVersion where id = :bookId")
    fun  changeLocalVersion(bookId: String, localVersion: String)
}
