package com.neobis.shoplistcleanarchitecture.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ComplexColorCompat
import androidx.recyclerview.widget.RecyclerView
import com.neobis.shoplistcleanarchitecture.R
import com.neobis.shoplistcleanarchitecture.domain.ShopItem
import java.lang.RuntimeException

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopListViewHolder>() {

    var count = 0
    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        Log.d("ShopListAdapter", "${++count}")
        val layout = when (viewType) {
            VIEW_TYPE_ENABLED -> R.layout.shop_item_enabled
            VIEW_TYPE_DISABLED -> R.layout.shop_item_disabled
            else -> throw RuntimeException("Unknown view type : ${viewType}")
        }

        val view = LayoutInflater.from(parent.context).inflate(
            layout,
            parent,
            false
        )

        return ShopListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        var itemView = shopList[position]
        holder.tvName.text = itemView.name
        holder.tvCount.text = itemView.counter.toString()
        holder.view.setOnLongClickListener {
            true
        }

        holder.tvName.text = itemView.name
        holder.tvCount.text = itemView.counter.toString()

    }

    override fun getItemViewType(position: Int): Int {
        if (shopList[position].isActive) {
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

    override fun onViewRecycled(holder: ShopListViewHolder) {
        super.onViewRecycled(holder)
        holder.tvName.text = ""
        holder.tvCount.text = ""
        holder.tvName.setTextColor(ContextCompat.getColor(holder.view.context, R.color.white))
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    class ShopListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)

    }
}