package ru.lantimat.my.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.lantimat.my.data.models.MenuCategory
import ru.lantimat.my.data.models.MenuItem
import java.io.IOException
import java.lang.reflect.Type

class DataSource(val context: Context) {

    fun getMainMenu(): List<MenuItem> {
        val gson = Gson()
        val jsonFileString: String? = getJsonDataFromAsset(context, "menu.json")
        val list: Type = object : TypeToken<List<MenuItem>>() {}.type
        return gson.fromJson(jsonFileString, list)
    }

    fun getCategories(): List<MenuCategory> {
        val gson = Gson()
        val jsonFileString: String? = getJsonDataFromAsset(context, "categories.json")
        val workoutsList: Type = object : TypeToken<List<MenuCategory>>() {}.type
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

}