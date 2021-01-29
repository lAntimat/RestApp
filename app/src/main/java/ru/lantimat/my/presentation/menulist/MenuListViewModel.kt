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
import ru.lantimat.my.data.models.MenuCategory
import ru.lantimat.my.presentation.menulist.models.MenuCategoryUi

@FlowPreview
@ExperimentalCoroutinesApi
class MenuListViewModel(private val dataSource: DataSource) : ViewModel() {

    private val channel = MutableSharedFlow<Unit>()

    val openNextScreen = SingleLiveEvent<Unit>()
    val items = MutableLiveData<MutableList<MenuAndHeader>>()
    val chips = MutableLiveData<MutableList<MenuCategoryUi>>()
    val chipsScrollPosition = MutableLiveData<Int>()
    val scrollPosition = MutableLiveData<Int>()
    val onScrollChangedDelayed = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch {
            channel
                .mapLatest {
                    delay(50)
                    onScrollChangedDelayed.postValue(false)
                }
                .collect()
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

        return list
    }

    private fun getChips(): MutableList<MenuCategoryUi> {
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

}