package ru.lantimat.my.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.lantimat.my.data.local.dao.BasketDishDao
import ru.lantimat.my.data.local.dao.MenuItemDao
import ru.lantimat.my.data.local.model.*
import java.io.IOException
import java.lang.reflect.Type

class DataSource(val context: Context, val menuItemDao: MenuItemDao, private val basketDishDao: BasketDishDao) {

    private fun getMainMenuFromJson(): List<MenuItem> {
        val gson = Gson()
        val jsonFileString: String? = getJsonDataFromAsset(context, "menu.json")
        val list: Type = object : TypeToken<List<MenuItem>>() {}.type
        return gson.fromJson(jsonFileString, list)
    }

    private fun getCategoriesFromJson(): List<MenuCategory> {
        val gson = Gson()
        val jsonFileString: String? = getJsonDataFromAsset(context, "categories.json")
        val workoutsList: Type = object : TypeToken<List<MenuCategory>>() {}.type
        return gson.fromJson(jsonFileString, workoutsList)
    }

    private fun getCompositionsFromJson(): List<Composition> {
        val gson = Gson()
        val jsonFileString: String? = getJsonDataFromAsset(context, "composition.json")
        val workoutsList: Type = object : TypeToken<List<Composition>>() {}.type
        return gson.fromJson(jsonFileString, workoutsList)
    }

    private fun getIngredientsFromJson(): List<Ingredient> {
        val gson = Gson()
        val jsonFileString: String? = getJsonDataFromAsset(context, "ingredients.json")
        val workoutsList: Type = object : TypeToken<List<Ingredient>>() {}.type
        return gson.fromJson(jsonFileString, workoutsList)
    }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    suspend fun addJsonToDatabase() {
        if (menuItemDao.findAllMenuItems().isEmpty()) {
            menuItemDao.insertCategories(getCategoriesFromJson())
            menuItemDao.insertComposition(getCompositionsFromJson())
            menuItemDao.insertIngredients(getIngredientsFromJson())
            menuItemDao.insert(getMainMenuFromJson())

        }
    }

    suspend fun getMainMenu(): List<MenuItem> {
        return menuItemDao.findAllMenuItems()
    }

    suspend fun getMainMenuById(id: Int): MenuItem? {
        return menuItemDao.find(id)
    }

    suspend fun findComposition(id: Int): CompositionWithIngredients? {
        return menuItemDao.findComposition(id)
    }

    suspend fun getCategories(): List<MenuCategory> {
        return menuItemDao.findAllMenuCategories()
    }

    suspend fun getBasket(): MutableList<BasketDishItem> {
        return basketDishDao.findAll()
    }

    suspend fun updateMenuItemCount(id: Int, count: Int) {
        menuItemDao.updateCount(id, count)
    }

}