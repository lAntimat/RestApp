package ru.lantimat.my.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.lantimat.my.data.local.model.MenuItem

@Dao
interface MenuItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: MenuItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: List<MenuItem>)

    @Query("DELETE FROM menu_item WHERE id = :id")
    fun delete(id: String)

    @Query("SELECT * FROM menu_item WHERE id = :id LIMIT 1")
    fun find(id: String): MenuItem?

    @Query("SELECT * FROM menu_item")
    fun findAll(): List<MenuItem>
}
