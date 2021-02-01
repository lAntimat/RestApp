package ru.lantimat.my.presentation.menulist

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.android.viewmodel.ext.android.viewModel
import ru.lantimat.my.R
import ru.lantimat.my.databinding.FragmentMenuListBinding
import ru.lantimat.my.presentation.BaseVmFragment


class MenuListFragment : BaseVmFragment(R.layout.fragment_menu_list) {

    private val viewModel: MenuListViewModel by viewModel()

    private val binding: FragmentMenuListBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.openNextScreen.subscribe {
            //findNavController().navigate()
        }

        initRecyclerViewChips()
        initRecyclerView()

        bindViewModel()

        binding.basketView.root.setOnClickListener { findNavController().navigate(R.id.basketFragment) }
    }

    private fun initRecyclerView() {
        val lm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.recyclerView.apply {
            layoutManager = lm
            adapter = MenuAdapter(
                itemClickListener = { id ->
                    val args = MenuListFragmentDirections.actionMenuListFragmentToDetailInfoFragment(id)
                    findNavController().navigate(args)
                },
                plusClickListener = {
                    viewModel.onPlusClick(it)
                }, minusClickListener = {
                    viewModel.onMinusClick(it)
                })
        }

        binding.recyclerView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            viewModel.onScrollChange()
        }

        (binding.recyclerView.adapter as MenuAdapter).apply {
            setOnBtnClickListener { viewModel.onAddToBasketClick(it) }
        }
    }

    private fun initRecyclerViewChips() {
        val lm = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewChips.apply {
            layoutManager = lm
            adapter = MenuChipsAdapter { menu, position ->
                viewModel.onChipClick(menu.id)
                binding.recyclerViewChips.smoothScrollToPosition(position)
            }
        }
    }

    private fun bindViewModel() {
        viewModel.items.subscribe {
            (binding.recyclerView.adapter as MenuAdapter).updateItems(it)
        }

        viewModel.chips.subscribe {
            (binding.recyclerViewChips.adapter as MenuChipsAdapter).updateItems(it)
        }

        viewModel.chipsScrollPosition.subscribe {
            binding.recyclerViewChips.smoothScrollToPosition(it)
        }

        viewModel.scrollPosition.subscribe {
            val smoothScroller: SmoothScroller = object : LinearSmoothScroller(context) {
                override fun getVerticalSnapPreference(): Int {
                    return SNAP_TO_START
                }

            }
            smoothScroller.targetPosition = it
            binding.recyclerView.layoutManager?.startSmoothScroll(smoothScroller)
        }

        viewModel.onScrollChangedDelayed.subscribe {
            val lm = binding.recyclerView.layoutManager as LinearLayoutManager
            lm.isSmoothScrolling.let { isSmoothScroller ->
                if (!isSmoothScroller) {
                    binding.recyclerView.findViewHolderForAdapterPosition(lm.findFirstVisibleItemPosition())
                        ?.let {
                            if (it is MenuAdapter.HeaderViewHolder) {
                                viewModel.selectChipById(id = it.item?.id ?: 0)
                            } else if (it is MenuAdapter.BodyViewHolder) {
                                viewModel.selectChipById(it.item?.categoryId ?: 0)
                            }
                        }
                }
            }
        }

        viewModel.ldBasketState.subscribe {
            when (it) {
                is BasketState.Gone -> binding.basketView.root.isVisible = false
                is BasketState.Visible -> {
                    binding.basketView.root.isVisible = true
                    binding.basketView.tvSum.text = it.sum
                }
            }
        }
    }
}