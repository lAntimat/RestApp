package ru.lantimat.my.presentation.basket

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
import ru.lantimat.my.databinding.FragmentBasketBinding
import ru.lantimat.my.databinding.FragmentMenuListBinding
import ru.lantimat.my.presentation.BaseVmFragment


class BasketFragment : BaseVmFragment(R.layout.fragment_basket) {

    private val viewModel: BasketViewModel by viewModel()

    private val binding: FragmentBasketBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.openNextScreen.subscribe {
            //findNavController().navigate()
        }

        initRecyclerView()
        bindViewModel()

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.ivDelete.setOnClickListener {
            viewModel.onDeleteClick()
        }
    }

    private fun initRecyclerView() {
        val lm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.recyclerView.apply {
            layoutManager = lm
            adapter = BasketAdapter(
                plusClickListener = {
                    viewModel.onPlusClick(it)
                }, minusClickListener = {
                    viewModel.onMinusClick(it)
                })
        }
    }

    private fun bindViewModel() {
        viewModel.items.subscribe {
            (binding.recyclerView.adapter as BasketAdapter).updateItems(it)
        }
    }
}