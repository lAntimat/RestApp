package ru.lantimat.my.presentation.menulist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ListMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.lantimat.my.data.models.MenuCategory
import ru.lantimat.my.data.models.MenuItem
import ru.lantimat.my.databinding.ItemMenuBinding
import ru.lantimat.my.databinding.ItemMenuChipBinding
import ru.lantimat.my.databinding.ItemMenuHeaderBinding
import ru.lantimat.my.presentation.menulist.models.MenuCategoryUi
import ru.lantimat.my.presentation.menulist.models.MenuItemType

class MenuChipsAdapter(
    private val items: MutableList<MenuCategoryUi> = mutableListOf(),
    private var itemClickListener: ((Int) -> Unit)? = null
) : RecyclerView.Adapter<MenuChipsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMenuChipBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    class ViewHolder(private val binding: ItemMenuChipBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MenuCategoryUi) {
            binding.chip.text = item.name
            binding.chip.isChecked = item.isChecked
        }
    }

    fun updateItems(newItems: MutableList<MenuCategoryUi>) {
        items.clear()
        items.addAll(newItems)
    }

}
