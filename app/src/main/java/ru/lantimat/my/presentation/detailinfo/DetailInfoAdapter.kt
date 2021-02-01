package ru.lantimat.my.presentation.detailinfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.lantimat.my.data.local.model.Ingredient
import ru.lantimat.my.databinding.ItemBasketBinding
import ru.lantimat.my.databinding.ItemDetailInfoIngredientBinding

class DetailInfoAdapter(
    private val items: MutableList<Ingredient> = mutableListOf(),
    private var itemClickListener: ((Int) -> Unit)? = null,
    private var plusClickListener: ((Int) -> Unit)? = null,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val bindingBody =
            ItemDetailInfoIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bindingBody)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> holder.bind(
                items[position],
                itemClickListener,
                plusClickListener,
                position
            )
        }
    }

    override fun getItemCount() = items.size

    fun setOnItemClickListener(itemClickListener: ((Int) -> Unit)) {
        this.itemClickListener = itemClickListener
    }

    fun updateItems(newItems: MutableList<Ingredient>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: ItemDetailInfoIngredientBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: Ingredient,
            itemClickListener: ((Int) -> Unit)? = null,
            plusClickListener: ((Int) -> Unit)? = null,
            position: Int,
        ) {
            binding.tvTitle.text = item.name
            binding.tvPrice.text = "${item.price} руб."
            binding.tvPlus.setOnClickListener { plusClickListener?.invoke(position) }
        }
    }

}
