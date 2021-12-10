package com.neobis.shoplistcleanarchitecture.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ComplexColorCompat
import androidx.recyclerview.widget.RecyclerView
import com.neobis.shoplistcleanarchitecture.R
import com.neobis.shoplistcleanarchitecture.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopListViewHolder>() {

    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.shop_item_disabled,
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
        var status = if(itemView.isActive){
            "Active"
        }else "Not Active"
        if(itemView.isActive){
            holder.tvName.text = "${itemView.name} $status"
            holder.tvCount.text = itemView.counter.toString()
            holder.tvName.setTextColor(ContextCompat.getColor(holder.view.context,R.color.purple_200 ))
        }else{
            holder.tvName.text = ""
            holder.tvCount.text = ""
            holder.tvName.setTextColor(ContextCompat.getColor(holder.view.context,R.color.white ))
        }
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    class ShopListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)

    }
}