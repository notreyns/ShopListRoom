package com.neobis.shoplistcleanarchitecture.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.neobis.shoplistcleanarchitecture.R
import com.neobis.shoplistcleanarchitecture.databinding.ShopItemDisabledBinding
import com.neobis.shoplistcleanarchitecture.databinding.ShopItemEnabledBinding
import com.neobis.shoplistcleanarchitecture.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopListViewHolder>(ShopItemDiffCallback()) {

    var onShopClickListener: ((ShopItem) -> Unit)? = null
    var onShopLongClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_ENABLED -> R.layout.shop_item_enabled
            VIEW_TYPE_DISABLED -> R.layout.shop_item_disabled
            else -> throw RuntimeException("Unknown view type : ${viewType}")
        }
        val view = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return ShopListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        var itemView = currentList[position]
        val binding = holder.binding
        when (binding) {
            is ShopItemDisabledBinding -> {
                binding.tvName.text = itemView.name
                binding.tvCount.text = itemView.counter.toString()
            }
            is ShopItemEnabledBinding -> {
                binding.tvName.text = itemView.name
                binding.tvCount.text = itemView.counter.toString()
            }
        }

        binding.root.setOnLongClickListener {
            onShopLongClickListener?.invoke(itemView)
            true
        }

        binding.root.setOnClickListener {
            onShopClickListener?.invoke(itemView)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (currentList[position].isActive) {
            return VIEW_TYPE_ENABLED
        } else {
            return VIEW_TYPE_DISABLED
        }
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLED = 2

        const val MAX_POOL_SIZE = 15
    }


}