package ru.lantimat.my.presentation.detailinfo

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import by.kirich1409.viewbindingdelegate.viewBinding
import com.squareup.picasso.Picasso
import org.koin.android.viewmodel.ext.android.viewModel
import ru.lantimat.my.R
import ru.lantimat.my.databinding.FragmentBasketBinding
import ru.lantimat.my.databinding.FragmentDetailInfoBinding
import ru.lantimat.my.databinding.FragmentMenuListBinding
import ru.lantimat.my.presentation.BaseVmFragment


class DetailInfoFragment : BaseVmFragment(R.layout.fragment_detail_info) {

    private val viewModel: DetailInfoViewModel by viewModel()

    private val binding: FragmentDetailInfoBinding by viewBinding()

    private val args: DetailInfoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.init(args.id)

        viewModel.openNextScreen.subscribe {
            //findNavController().navigate()
        }

        initRecyclerView()

        bindViewModel()
    }

    private fun initRecyclerView() {
        val lm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.recyclerView.apply {
            layoutManager = lm
            adapter = DetailInfoAdapter(
                plusClickListener = {
                    viewModel.onPlusClick(it)
                })
        }
    }

    private fun bindViewModel() {
        viewModel.ingredients.subscribe {
            (binding.recyclerView.adapter as DetailInfoAdapter).updateItems(it.ingredients.toMutableList())

            binding.tvTitle.text = it.composition.name
        }

        viewModel.item.subscribe {
            binding.toolbar.title = it.name

            Picasso.get()
                .load(it.imgUrl)
                .into(binding.appBarImage)
        }
    }
}