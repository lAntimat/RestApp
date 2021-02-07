package ru.lantimat.my.presentation.basket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.lantimat.my.data.DataSource
import ru.lantimat.my.data.SingleLiveEvent
import ru.lantimat.my.data.local.dao.BasketDishDao
import ru.lantimat.my.data.local.dao.MenuItemDao
import ru.lantimat.my.data.local.model.BasketDishItem
import ru.lantimat.my.data.local.model.MenuItem
import ru.lantimat.my.data.local.model.MenuCategory
import ru.lantimat.my.presentation.menulist.models.MenuCategoryUi
import ru.lantimat.my.util.extension.refresh
import timber.log.Timber

@FlowPreview
@ExperimentalCoroutinesApi
class BasketViewModel(private val dataSource: DataSource, private val basketDishDao: BasketDishDao) : ViewModel() {

    val openNextScreen = SingleLiveEvent<Unit>()
    val items = MutableLiveData<MutableList<BasketDishItem>>()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            items.value = dataSource.getBasket()
        }
    }

    fun onPlusClick(position: Int) {
        viewModelScope.launch {
            val item = items.value?.get(position)
            item!!.count++
            basketDishDao.updateCount(item.id, item.count)
            items.refresh()
        }
    }

    fun onMinusClick(position: Int) {
        viewModelScope.launch {
            val item = items.value?.get(position)
            item!!.count--
            if(item.count == 0) {
                item.count = 1
                return@launch
            }
            basketDishDao.updateCount(item.id, item.count)
            items.refresh()
        }
    }

    fun onDeleteClick() {
        viewModelScope.launch {
            items.value!!.forEach {
                dataSource.updateMenuItemCount(it.id, 0)
            }
            basketDishDao.deleteAll()
            loadData()
        }
    }
}