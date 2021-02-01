package ru.lantimat.my.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.lantimat.my.data.local.model.BasketDishItem

@Dao
interface BasketDishDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: BasketDishItem)

    @Query("UPDATE basket_dish SET count = :count")
    suspend fun updateCount(count: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: List<BasketDishItem>)

    @Query("DELETE FROM basket_dish WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM basket_dish WHERE id = :id LIMIT 1")
    suspend fun find(id: Int): BasketDishItem?

    @Query("SELECT * FROM basket_dish")
    suspend fun findAll(): MutableList<BasketDishItem>
}
