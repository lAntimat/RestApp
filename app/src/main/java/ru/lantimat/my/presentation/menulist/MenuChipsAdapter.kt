package ru.lantimat.my.presentation.menulist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.lantimat.my.databinding.ItemMenuChipBinding
import ru.lantimat.my.presentation.menulist.models.MenuCategoryUi

class MenuChipsAdapter(
    private val items: MutableList<MenuCategoryUi> = mutableListOf(),
    private var itemClickListener: ((MenuCategoryUi, Int) -> Unit)? = null
) : RecyclerView.Adapter<MenuChipsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMenuChipBinding.inflate(LayoutInflater.from(parent.context), parent, false), itemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount() = items.size

    class ViewHolder(
        private val binding: ItemMenuChipBinding,
        private val itemClickListener: ((MenuCategoryUi, Int) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MenuCategoryUi, position: Int) {
            binding.chip.text = item.name
            binding.chip.isChecked = item.isChecked

            binding.chip.setOnClickListener {
                itemClickListener?.invoke(item, position)
            }
        }
    }

    fun updateItems(newItems: MutableList<MenuCategoryUi>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
