package ru.lantimat.my.presentation.menulist

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
import timber.log.Timber

@FlowPreview
@ExperimentalCoroutinesApi
class MenuListViewModel(
    private val dataSource: DataSource,
    private val basketDishDao: BasketDishDao
) : ViewModel() {

    private val channel = MutableSharedFlow<Unit>()

    val openNextScreen = SingleLiveEvent<Unit>()
    val items = MutableLiveData<MutableList<MenuAndHeader>>()
    val chips = MutableLiveData<MutableList<MenuCategoryUi>>()
    val chipsScrollPosition = MutableLiveData<Int>()
    val scrollPosition = MutableLiveData<Int>()
    val onScrollChangedDelayed = MutableLiveData<Boolean>()
    val ldBasketState = MutableLiveData<BasketState>()

    init {
        //FIXME Добавляю моки в дб
        viewModelScope.launch {
            dataSource.addJsonToDatabase()

            items.value = getCombinedList()
            chips.value = getChips()
            checkBasket()
        }

        viewModelScope.launch {
            channel
                .mapLatest {
                    delay(50)
                    onScrollChangedDelayed.postValue(false)
                }
                .collect()
        }

    }

    private suspend fun getCombinedList(): MutableList<MenuAndHeader> {
        val menuCategories = dataSource.getCategories()
        val groupList = combineBasketAndMenuItems().groupBy { it.categoryId }

        val list = mutableListOf<MenuAndHeader>()

        menuCategories.forEach { category ->
            list.add(category)
            list.addAll(groupList[category.id]?.toMutableList() ?: listOf())
        }

        return list
    }

    private suspend fun combineBasketAndMenuItems(): List<MenuItem> {
        val mainItems = dataSource.getMainMenu()
        val basket = dataSource.getBasket()

        basket.forEach { item ->
            mainItems.find { it.id == item.id }?.count = item.count
        }

        return mainItems
    }

    private suspend fun getChips(): MutableList<MenuCategoryUi> {
        return dataSource.getCategories().map {
            MenuCategoryUi(it.id, it.name)
        }.toMutableList()
    }

    fun onScrollChange() {
        viewModelScope.launch {
            channel.emit(Unit)
        }
    }

    fun selectChipById(id: Int) {
        var position = 0
        chips.value?.forEachIndexed { index, menuCategoryUi ->
            if (menuCategoryUi.id == id) {
                menuCategoryUi.isChecked = true
                position = index
            } else menuCategoryUi.isChecked = false
        }
        chips.postValue(chips.value)
        chipsScrollPosition.postValue(position)

    }

    fun onChipClick(id: Int) {
        var position = 0
        items.value?.forEachIndexed { index, menu ->
            if (menu is MenuCategory) {
                if (menu.id == id) position = index
            }
        }

        chips.value?.forEach { menuCategoryUi ->
            menuCategoryUi.isChecked = menuCategoryUi.id == id
        }

        chips.postValue(chips.value)
        scrollPosition.postValue(position)
    }

    fun onAddToBasketClick(position: Int) {
        viewModelScope.launch {
            val item = (items.value?.getOrNull(position) as MenuItem)
            item.count++

            basketDishDao.insert(BasketDishItem(item.id, item.name, item.imgUrl, item.count, item.price))
            items.value = items.value

            checkBasket()
        }
    }

    fun onPlusClick(position: Int) {
        viewModelScope.launch {
            val item = (items.value?.getOrNull(position) as MenuItem)
            item.count++

            basketDishDao.updateCount(item.count)

            items.value = items.value

            checkBasket()
        }
    }

    fun onMinusClick(position: Int) {
        viewModelScope.launch {
            val item = (items.value?.getOrNull(position) as MenuItem)
            item.count--

            if (item.count == 0) basketDishDao.delete(item.id)
            else basketDishDao.updateCount(item.count)
            items.value = items.value

            checkBasket()
        }
    }

    private suspend fun checkBasket() {
        val basket = dataSource.getBasket()

        if (basket.isNotEmpty()) {
            val sum = basket.sumByDouble { it.price.toDouble() * it.count }
            ldBasketState.value = BasketState.Visible("$sum руб.")
        } else {
            ldBasketState.value = BasketState.Gone
        }
    }

}