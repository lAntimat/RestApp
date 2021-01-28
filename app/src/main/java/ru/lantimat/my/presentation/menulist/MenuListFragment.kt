package ru.lantimat.my.presentation.menulist

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import ru.lantimat.my.R
import ru.lantimat.my.data.DataSource
import ru.lantimat.my.data.models.MenuCategory
import ru.lantimat.my.databinding.FragmentMenuListBinding
import ru.lantimat.my.presentation.BaseVmFragment
import ru.lantimat.my.presentation.menulist.models.MenuCategoryUi

class MenuListFragment : BaseVmFragment(R.layout.fragment_menu_list) {

    private val viewModel: MenuListViewModel by viewModel()

    private val binding: FragmentMenuListBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.openNextScreen.subscribe {
            //findNavController().navigate()
        }

        bindViewModel()
    }

    private fun initRecyclerView(items: MutableList<MenuAndHeader>) {
        val lm = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)

        binding.recyclerView.apply {
            layoutManager = lm
            adapter = MenuAdapter(items)
        }

        binding.recyclerView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            binding.recyclerView.findViewHolderForAdapterPosition(lm.findFirstVisibleItemPosition())
                ?.let {
                    if (it is MenuAdapter.HeaderViewHolder) {
                        it.item?.let { menuCategory ->
                            viewModel.setVisibleHeaderItem(menuCategory)
                        }
                    }
                }
        }
    }

    private fun initRecyclerViewChips(items: MutableList<MenuCategoryUi>) {
        val lm = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
        binding.recyclerViewChips.apply {
            layoutManager = lm
            adapter = MenuChipsAdapter(items)
        }
    }

    private fun bindViewModel() {
        viewModel.items.subscribe {
            initRecyclerView(it)
        }

        viewModel.chips.subscribe {
            initRecyclerViewChips(it)
        }

        viewModel.chipsScrollPosition.subscribe {
            binding.recyclerViewChips.smoothScrollToPosition(it)
        }
    }
}