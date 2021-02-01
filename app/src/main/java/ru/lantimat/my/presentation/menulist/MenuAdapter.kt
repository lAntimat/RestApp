package ru.lantimat.my.presentation.menulist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.lantimat.my.data.local.model.MenuCategory
import ru.lantimat.my.data.local.model.MenuItem
import ru.lantimat.my.databinding.ItemMenuBinding
import ru.lantimat.my.databinding.ItemMenuHeaderBinding
import ru.lantimat.my.presentation.menulist.models.MenuItemType

class MenuAdapter(
    private val items: MutableList<MenuAndHeader> = mutableListOf(),
    private var itemClickListener: ((Int) -> Unit)? = null,
    private var btnClickListener: ((Int) -> Unit)? = null,
    private var plusClickListener: ((Int) -> Unit)? = null,
    private var minusClickListener: ((Int) -> Unit)? = null,
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
            is BodyViewHolder -> holder.bind(
                items[position] as MenuItem,
                itemClickListener,
                btnClickListener,
                plusClickListener,
                minusClickListener,
                position
            )
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

    fun updateItems(newItems: MutableList<MenuAndHeader>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    class BodyViewHolder(private val binding: ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var item: MenuItem? = null
        fun bind(
            item: MenuItem,
            itemClickListener: ((Int) -> Unit)?,
            btnClickListener: ((Int) -> Unit)?,
            plusClickListener: ((Int) -> Unit)?,
            minusClickListener: ((Int) -> Unit)?,
            position: Int
        ) {
            this.item = item
            binding.tvTitle.text = item.name
            binding.tvDescription.text = item.description
            binding.tvPrice.text = "${item.price} руб."

            if (item.descriptionStop.isNotEmpty()) {
                binding.tvStatus.text = item.descriptionStop
                binding.button.isEnabled = false
            } else {
                binding.button.isEnabled = true
                binding.tvStatus.text = ""
            }

            if (item.count > 0) {
                binding.apply {
                    llChangeCount.isVisible = true
                    button.isVisible = false
                    tvCount.text = item.count.toString()
                }
            } else {
                binding.apply {
                    llChangeCount.isVisible = false
                    button.isVisible = true
                }
            }

            binding.button.setOnClickListener { btnClickListener?.invoke(position) }

            binding.tvPlus.setOnClickListener { plusClickListener?.invoke(position) }
            binding.tvMinus.setOnClickListener { minusClickListener?.invoke(position) }
            binding.root.setOnClickListener { itemClickListener?.invoke(item.id) }

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
