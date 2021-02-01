package ru.lantimat.my.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.lantimat.my.data.local.model.*

@Dao
interface MenuItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: MenuItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: List<MenuItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(item: List<MenuCategory>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredients(item: List<Ingredient>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComposition(item: List<Composition>)

    @Query("DELETE FROM menu_item WHERE id = :id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM menu_item WHERE id = :id LIMIT 1")
    suspend fun find(id: Int): MenuItem?

    @Query("SELECT * FROM composition WHERE id = :id LIMIT 1")
    suspend fun findComposition(id: Int): CompositionWithIngredients?

    @Query("SELECT * FROM menu_item")
    suspend fun findAllMenuItems(): List<MenuItem>

    @Query("SELECT * FROM menu_category")
    suspend fun findAllMenuCategories(): List<MenuCategory>
}
