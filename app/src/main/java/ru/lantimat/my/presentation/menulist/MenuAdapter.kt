package ru.lantimat.my.presentation.menulist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.lantimat.my.data.models.MenuCategory
import ru.lantimat.my.data.models.MenuItem
import ru.lantimat.my.databinding.ItemMenuBinding
import ru.lantimat.my.databinding.ItemMenuHeaderBinding
import ru.lantimat.my.presentation.menulist.models.MenuCategoryUi
import ru.lantimat.my.presentation.menulist.models.MenuItemType

class MenuAdapter(
    private val items: List<MenuAndHeader> = listOf(),
    private var itemClickListener: ((Int) -> Unit)? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val bindingBody = ItemMenuBinding.inflate(LayoutInflater.from(parent.context))
        val bindingHeader = ItemMenuHeaderBinding.inflate(LayoutInflater.from(parent.context))

        return when (viewType) {
            MenuItemType.Header.ordinal -> HeaderViewHolder(bindingHeader)
            MenuItemType.Body.ordinal -> BodyViewHolder(bindingBody)
            else -> BodyViewHolder(bindingBody)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BodyViewHolder -> holder.bind(items[position] as MenuItem)
            is HeaderViewHolder -> holder.bind(items[position] as MenuCategory)
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return if (items[position] is MenuItem) MenuItemType.Body.ordinal else MenuItemType.Header.ordinal
    }

    class BodyViewHolder(private val binding: ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MenuItem) {
            binding.tvTitle.text = item.name
            binding.tvDescription.text = item.description

            Picasso.get()
                .load(item.imgUrl)
                .resize(200, 200)
                .centerCrop()
                .into(binding.ivImage)
        }
    }

    class HeaderViewHolder(private val binding: ItemMenuHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var item: MenuCategory? = null
        fun bind(item: MenuCategory) {
            this.item = item
            binding.tvTitle.text = item.name
        }
    }

}
