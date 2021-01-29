package ru.lantimat.my.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.lantimat.my.data.local.model.BasketDishItem

@Dao
interface DishDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: BasketDishItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: List<BasketDishItem>)

    @Query("DELETE FROM basket_dish WHERE id = :id")
    fun delete(id: String)

    @Query("SELECT * FROM basket_dish WHERE id = :id LIMIT 1")
    fun find(id: String): BasketDishItem?

    @Query("SELECT * FROM basket_dish")
    fun findAll(): List<BasketDishItem>
}
