package ru.lantimat.my.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.lantimat.my.data.local.dao.BasketDishDao
import ru.lantimat.my.data.local.dao.MenuItemDao
import ru.lantimat.my.data.local.model.BasketDishItem
import ru.lantimat.my.data.local.model.MenuItem

@Database(
    entities = [
        BasketDishItem::class,
        MenuItem::class,
    ], version = 1
)
abstract class DishesDatabase : RoomDatabase() {

    abstract val basketDishDao: BasketDishDao
    abstract val menuDao: MenuItemDao
}
