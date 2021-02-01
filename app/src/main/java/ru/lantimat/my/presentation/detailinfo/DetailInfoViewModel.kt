package ru.lantimat.my.presentation.detailinfo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.lantimat.my.data.DataSource
import ru.lantimat.my.data.SingleLiveEvent
import ru.lantimat.my.data.local.dao.BasketDishDao
import ru.lantimat.my.data.local.model.CompositionWithIngredients
import ru.lantimat.my.data.local.model.Ingredient
import ru.lantimat.my.data.local.model.MenuItem

@FlowPreview
@ExperimentalCoroutinesApi
class DetailInfoViewModel(private val dataSource: DataSource, private val basketDishDao: BasketDishDao) : ViewModel() {
    val openNextScreen = SingleLiveEvent<Unit>()
    val ingredients = MutableLiveData<CompositionWithIngredients>()
    val item = MutableLiveData<MenuItem>()

    fun init(id: Int) {
        viewModelScope.launch {
            val menu = dataSource.getMainMenuById(id)!!
            dataSource.findComposition(menu.idPattern1)?.let {
                ingredients.value = it
            }

            item.value = menu
        }
    }

    fun onPlusClick(position: Int) {
        viewModelScope.launch {

        }
    }
}