package ru.lantimat.my.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.lantimat.my.data.local.model.BasketDishItem
import ru.lantimat.my.data.local.model.Ingredient

@Dao
interface BasketDishDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: BasketDishItem)

    @Query("UPDATE basket_dish SET count = :count WHERE id =:id")
    suspend fun updateCount(id: Int, count: Int)

    @Query("UPDATE basket_dish SET ingredients = :ingredients WHERE id =:id")
    suspend fun updateIngredients(id: Int, ingredients: String)

    @Query("UPDATE basket_dish SET `without` = :without WHERE id =:id")
    suspend fun updateWithout(id: Int, without: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: List<BasketDishItem>)

    @Query("DELETE FROM basket_dish WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM basket_dish")
    suspend fun deleteAll()

    @Query("SELECT * FROM basket_dish WHERE id = :id LIMIT 1")
    suspend fun find(id: Int): BasketDishItem?

    @Query("SELECT * FROM basket_dish")
    suspend fun findAll(): MutableList<BasketDishItem>
}
