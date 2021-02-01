package ru.lantimat.my.presentation.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.lantimat.my.data.local.model.BasketDishItem
import ru.lantimat.my.data.local.model.MenuCategory
import ru.lantimat.my.data.local.model.MenuItem
import ru.lantimat.my.databinding.ItemBasketBinding
import ru.lantimat.my.databinding.ItemMenuBinding
import ru.lantimat.my.databinding.ItemMenuHeaderBinding
import ru.lantimat.my.presentation.menulist.models.MenuItemType

class BasketAdapter(
    private val items: MutableList<BasketDishItem> = mutableListOf(),
    private var itemClickListener: ((Int) -> Unit)? = null,
    private var plusClickListener: ((Int) -> Unit)? = null,
    private var minusClickListener: ((Int) -> Unit)? = null,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val bindingBody =
            ItemBasketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bindingBody)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> holder.bind(
                items[position],
                itemClickListener,
                plusClickListener,
                minusClickListener,
                position
            )
        }
    }

    override fun getItemCount() = items.size

    fun setOnItemClickListener(itemClickListener: ((Int) -> Unit)) {
        this.itemClickListener = itemClickListener
    }

    fun updateItems(newItems: MutableList<BasketDishItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: ItemBasketBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: BasketDishItem,
            itemClickListener: ((Int) -> Unit)? = null,
            plusClickListener: ((Int) -> Unit)? = null,
            minusClickListener: ((Int) -> Unit)? = null,
            position: Int,
        ) {
            binding.tvTitle.text = item.name
            binding.tvCount.text = item.count.toString()
            binding.tvPrice.text = "${item.price} руб."
            binding.tvPlus.setOnClickListener { plusClickListener?.invoke(position) }
            binding.tvMinus.setOnClickListener { minusClickListener?.invoke(position) }

            Picasso.get()
                .load(item.imgUrl)
                .centerCrop()
                .resize(200, 200)
                .into(binding.ivImage)
        }
    }

}
