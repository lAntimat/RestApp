package ru.lantimat.my.presentation.detailinfo

import android.text.TextUtils
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
import ru.lantimat.my.data.local.model.*
import ru.lantimat.my.presentation.models.IngredientUi
import ru.lantimat.my.util.extension.refresh

@FlowPreview
@ExperimentalCoroutinesApi
class DetailInfoViewModel(
    private val dataSource: DataSource,
    private val basketDishDao: BasketDishDao
) : ViewModel() {
    var id = 0
    val exit = SingleLiveEvent<Unit>()
    val ingredients = MutableLiveData<List<IngredientUi>>()
    val withoutIngredients = MutableLiveData<List<IngredientUi>>()
    val composition = MutableLiveData<Composition>()
    val item = MutableLiveData<MenuItem>()
    val bottomPanelState = MutableLiveData<BottomPanelState>()
    val topPanelVisibility = MutableLiveData(false)

    val composition2 = MutableLiveData<Composition>()
    val ingredients2 = MutableLiveData<List<IngredientUi>>()


    val addedIngredients = hashMapOf<Int, IngredientUi>()

    fun init(id: Int) {
        this.id = id
        viewModelScope.launch {
            val menu = dataSource.getMainMenuById(id)!!
            item.value = menu

            bottomPanelState.value = if(menu.count == 0) BottomPanelState.Buy else BottomPanelState.Counter

            dataSource.findComposition(menu.idPattern1)?.let {
                ingredients.value = it.ingredients.map { it.toUi() }
                composition.value = it.composition
            }

            dataSource.findComposition(menu.idPattern2)?.let {
                ingredients2.value = it.ingredients.map { it.toUi() }
                composition2.value = it.composition
            }
        }
    }

    fun onCheckClick(position: Int) {
        viewModelScope.launch {
            ingredients.value!![position].isChecked = !ingredients.value!![position].isChecked
            ingredients.refresh()
        }
    }

    fun onCheckClick2(position: Int) {
        viewModelScope.launch {
            ingredients2.value!![position].isChecked = !ingredients2.value!![position].isChecked
            //ingredients2.refresh()
        }
    }

    fun onPlusClick() {
        item.value!!.count++
        item.refresh()
        viewModelScope.launch {
            basketDishDao.updateCount(id, item.value!!.count)
            dataSource.updateMenuItemCount(id, item.value!!.count)

        }
    }

    fun onMinusClick() {
        item.value!!.count--
        if (item.value!!.count == 0) {
            item.value!!.count = 1
            return
        }
        item.refresh()
        viewModelScope.launch {
            basketDishDao.updateCount(id, item.value!!.count)
            dataSource.updateMenuItemCount(id, item.value!!.count)
        }
    }

    fun onSaveClick() {
        if (bottomPanelState.value is BottomPanelState.Buy) {
            item.value!!
            item.value!!.count++
            viewModelScope.launch {
                basketDishDao.insert(
                    BasketDishItem(
                        item.value!!.id,
                        item.value!!.name,
                        item.value!!.imgUrl,
                        item.value!!.count,
                        item.value!!.price
                    )
                )
                item.refresh()
                bottomPanelState.value = BottomPanelState.Counter
            }
        } else if (bottomPanelState.value is BottomPanelState.Counter) {
            val addItems = ingredients.value!!.filter { it.isChecked }
            val removeItems = ingredients2.value!!.filter { it.isChecked }

            viewModelScope.launch {
                basketDishDao.updateIngredients(id, TextUtils.join(",", addItems.map { it.name }))
                basketDishDao.updateWithout(id, TextUtils.join(",", removeItems.map { it.name }))

                exit.postValue(Unit)
            }
        }
    }

    fun onInfoClick() {
        topPanelVisibility.value = topPanelVisibility.value?.not()
    }
}