package com.ggr3ml1n.shoppinglist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ggr3ml1n.shoppinglist.R
import com.ggr3ml1n.shoppinglist.databinding.ListNameItemBinding
import com.ggr3ml1n.shoppinglist.entities.ShoppingListName

class ShopListNameAdapter: ListAdapter<ShoppingListName, ShopListNameAdapter.ItemHolder>(ItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder = ItemHolder.create(parent)

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position))
    }

    class ItemHolder(view: View): RecyclerView.ViewHolder(view) {
            private val binding = ListNameItemBinding.bind(view)

        fun setData(shopListNameItem: ShoppingListName) = with(binding) {
            tvListName.text = shopListNameItem.name
            tvTime.text = shopListNameItem.time
        }

        companion object {
                fun create(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.list_name_item,
                        parent,
                        false
                    )
                )
            }
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<ShoppingListName>() {
        override fun areItemsTheSame(
            oldItem: ShoppingListName,
            newItem: ShoppingListName
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ShoppingListName,
            newItem: ShoppingListName
        ): Boolean = oldItem == newItem

    }
}