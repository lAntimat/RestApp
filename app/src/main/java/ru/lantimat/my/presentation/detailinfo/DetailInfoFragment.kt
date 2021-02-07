package ru.lantimat.my.presentation.detailinfo

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.squareup.picasso.Picasso
import org.koin.android.viewmodel.ext.android.viewModel
import ru.lantimat.my.R
import ru.lantimat.my.databinding.FragmentDetailInfoBinding
import ru.lantimat.my.presentation.BaseVmFragment


class DetailInfoFragment : BaseVmFragment(R.layout.fragment_detail_info) {

    private val viewModel: DetailInfoViewModel by viewModel()

    private val binding: FragmentDetailInfoBinding by viewBinding()

    private val args: DetailInfoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.init(args.id)

        viewModel.exit.subscribe {
            findNavController().popBackStack()
        }

        initRecyclerView()
        initRecyclerView2()

        bindViewModel()

        binding.bottomPanel.btnSave.setOnClickListener { viewModel.onSaveClick() }
        binding.bottomPanel.tvPlus.setOnClickListener { viewModel.onPlusClick() }
        binding.bottomPanel.tvMinus.setOnClickListener { viewModel.onMinusClick() }

        binding.ivInfo.setOnClickListener {
            viewModel.onInfoClick()
        }

        binding.topPanel.root.setOnClickListener {
            viewModel.onInfoClick()
        }


    }

    private fun initRecyclerView() {
        val lm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.recyclerView.apply {
            layoutManager = lm
            adapter = DetailInfoIngredientsAdapter(
                plusClickListener = {
                    viewModel.onCheckClick(it)
                })
        }
    }

    private fun initRecyclerView2() {
        val lm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.recyclerView2.apply {
            layoutManager = lm
            adapter = DetailInfoWithoutAdapter(
                plusClickListener = {
                    viewModel.onCheckClick2(it)
                })
        }
    }

    private fun bindViewModel() {
        viewModel.ingredients.subscribe {
            (binding.recyclerView.adapter as DetailInfoIngredientsAdapter).updateItems(it.toMutableList())
        }

        viewModel.composition.subscribe {
            binding.tvTitle.text = it.name
        }

        viewModel.ingredients2.subscribe {
            (binding.recyclerView2.adapter as DetailInfoWithoutAdapter).updateItems(it.toMutableList())
        }

        viewModel.composition2.subscribe {
            binding.tvTitle2.text = it.name
        }

        viewModel.item.subscribe {
            binding.toolbar.title = it.name
            binding.bottomPanel.tvCount.text = it.count.toString()

            Picasso.get()
                .load(it.imgUrl)
                .into(binding.appBarImage)
        }

        viewModel.bottomPanelState.subscribe {
            when(it) {
                BottomPanelState.Buy -> {
                    binding.apply {
                        bottomPanel.btnSave.text = "Добавить"
                        bottomPanel.llChangeCount.isVisible = false
                    }
                }
                BottomPanelState.Counter -> {
                    binding.apply {
                        bottomPanel.btnSave.text = "Сохранить"
                        bottomPanel.llChangeCount.isVisible = true
                    }
                }
            }
        }

        viewModel.topPanelVisibility.subscribe {
            binding.topPanel.root.isVisible = it
        }
    }
}