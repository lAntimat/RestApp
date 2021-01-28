package ru.lantimat.my.presentation.menulist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.lantimat.my.data.DataSource
import ru.lantimat.my.data.SingleLiveEvent
import ru.lantimat.my.data.models.MenuCategory
import ru.lantimat.my.presentation.menulist.models.MenuCategoryUi

class MenuListViewModel(private val dataSource: DataSource): ViewModel() {

    val openNextScreen = SingleLiveEvent<Unit>()
    val items = MutableLiveData<MutableList<MenuAndHeader>>()
    val chips = MutableLiveData<MutableList<MenuCategoryUi>>()
    val chipsScrollPosition = MutableLiveData<Int>()

    init {
        viewModelScope.launch {

        }

        items.postValue(getCombinedList())
        chips.postValue(getChips())
    }

    private fun getCombinedList(): MutableList<MenuAndHeader> {
        val menuCategories = dataSource.getCategories()
        val groupList = dataSource.getMainMenu().groupBy { it.categoryId }

        val list = mutableListOf<MenuAndHeader>()

        menuCategories.forEach { category ->
            list.add(category)
            list.addAll(groupList[category.id]?.toMutableList() ?: listOf())
        }

        return  list
    }

    private fun getChips(): MutableList<MenuCategoryUi> {
        return dataSource.getCategories().map {
            MenuCategoryUi(it.id, it.name)
        }.toMutableList()
    }

    fun setVisibleHeaderItem(item: MenuCategory) {
        selectChipById(id = item.id)
    }

    private fun selectChipById(id: Int) {
        chips.value?.forEachIndexed { index, menuCategoryUi ->
            if (menuCategoryUi.id == id) {
                menuCategoryUi.isChecked = true
                chipsScrollPosition.postValue(index)
            } else menuCategoryUi.isChecked = false
        }
        chips.postValue(chips.value)
    }

}