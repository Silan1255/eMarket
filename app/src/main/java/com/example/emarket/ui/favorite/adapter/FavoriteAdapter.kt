package com.example.emarket.ui.favorite.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.emarket.R
import com.example.emarket.data.model.FavoriteProductList

class FavoriteAdapter(val listener: OnFavoriteClicked, private val context: Context) : ListAdapter<FavoriteProductList,
        FavoriteAdapter.FavoriteProductViewHolder>(CartDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteProductViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_list, parent, false)
        return FavoriteProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FavoriteProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productName: TextView = itemView.findViewById(R.id.product_name)
        private val productPrice: TextView = itemView.findViewById(R.id.product_price)
        private val productImage: ImageView = itemView.findViewById(R.id.img_product)
        private val btnFavorite: ImageView = itemView.findViewById(R.id.btn_favorite)

        fun bind(product: FavoriteProductList) {
            productName.text = product.name
            productPrice.text = product.price
            Glide.with(context).load(product.image).placeholder(R.drawable.bg_product).into(productImage)

            btnFavorite.setOnClickListener {
                listener.onFavoriteClicked(product)
            }
        }

    }
}


class CartDiffCallback : DiffUtil.ItemCallback<FavoriteProductList>() {
    override fun areItemsTheSame(
        oldItem: FavoriteProductList,
        newItem: FavoriteProductList
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: FavoriteProductList,
        newItem: FavoriteProductList
    ): Boolean {
        return oldItem == newItem
    }
}

interface OnFavoriteClicked {
    fun onFavoriteClicked(favoriteProductList: FavoriteProductList)
}
