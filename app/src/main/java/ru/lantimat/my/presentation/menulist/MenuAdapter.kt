package ru.lantimat.my.presentation.menulist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.lantimat.my.data.models.MenuCategory
import ru.lantimat.my.data.local.model.MenuItem
import ru.lantimat.my.databinding.ItemMenuBinding
import ru.lantimat.my.databinding.ItemMenuHeaderBinding
import ru.lantimat.my.presentation.menulist.models.MenuItemType

class MenuAdapter(
    private val items: List<MenuAndHeader> = listOf(),
    private var itemClickListener: ((Int) -> Unit)? = null,
    private var btnClickListener: ((Int) -> Unit)? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val bindingBody =
            ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val bindingHeader =
            ItemMenuHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return when (viewType) {
            MenuItemType.Header.ordinal -> HeaderViewHolder(bindingHeader)
            MenuItemType.Body.ordinal -> BodyViewHolder(bindingBody)
            else -> BodyViewHolder(bindingBody)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BodyViewHolder -> holder.bind(items[position] as MenuItem, itemClickListener, btnClickListener, position)
            is HeaderViewHolder -> holder.bind(items[position] as MenuCategory)
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return if (items[position] is MenuItem) MenuItemType.Body.ordinal else MenuItemType.Header.ordinal
    }

    fun setOnItemClickListener(itemClickListener: ((Int) -> Unit)) {
        this.itemClickListener = itemClickListener
    }

    fun setOnBtnClickListener(btnClickListener: ((Int) -> Unit)) {
        this.btnClickListener = btnClickListener
    }

    class BodyViewHolder(private val binding: ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var item: MenuItem? = null
        fun bind(
            item: MenuItem,
            itemClickListener: ((Int) -> Unit)?,
            btnClickListener: ((Int) -> Unit)?,
            position: Int
        ) {
            this.item = item
            binding.tvTitle.text = item.name
            binding.tvDescription.text = item.description
            binding.tvPrice.text = "${item.price} руб."

            binding.button.setOnClickListener { btnClickListener?.invoke(position) }

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
