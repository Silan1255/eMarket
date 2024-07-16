package com.example.emarket.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.emarket.R
import com.example.emarket.data.model.ProductList

class EMarketAdapter(
    private val context: Context,
    private val listener: EMarketListener,
) : RecyclerView.Adapter<EMarketAdapter.EMarketViewHolder>() {

    private var productList: MutableList<ProductList> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EMarketViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_list, parent, false)
        return EMarketViewHolder(view)
    }

    fun submitList(list: List<ProductList>) {
        productList.clear()
        productList.addAll(list)
        notifyDataSetChanged()
    }

    fun addItems(list: List<ProductList>) {
        val startPosition = productList.size
        productList.addAll(list)
        notifyItemRangeInserted(startPosition, list.size)
    }

    override fun onBindViewHolder(holder: EMarketViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = productList.size


    inner class EMarketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productName: TextView = itemView.findViewById(R.id.product_name)
        private val productPrice: TextView = itemView.findViewById(R.id.product_price)
        private val productImage: ImageView = itemView.findViewById(R.id.img_product)
        private val addCart: Button = itemView.findViewById(R.id.btn_add_cart)
        private val btnFavorite: ImageView = itemView.findViewById(R.id.btn_favorite)


        fun bind(product: ProductList) {
            productName.text = product.name
            Glide.with(context).load(product.image).placeholder(R.drawable.bg_product)
                .into(productImage)

            productPrice.text = product.price

            itemView.setOnClickListener {
                listener.onEMarketClicked(product)
            }

            addCart.setOnClickListener {
                listener.onAddToCartClicked(product)
            }

            btnFavorite.setOnClickListener {
                listener.onFavoriteClicked(product)
            }

            btnFavorite.setOnClickListener {
                listener.onFavoriteClicked(product)
                updateFavoriteIcon(true)
            }
        }

        private fun updateFavoriteIcon(isFavorite: Boolean) {
            btnFavorite.setImageResource(
                if (isFavorite) R.drawable.ic_product_selected
                else R.drawable.ic_product_unselected
            )
        }
    }
}

interface EMarketListener {
    fun onEMarketClicked(productList: ProductList)
    fun onAddToCartClicked(productList: ProductList)
    fun onFavoriteClicked(productList: ProductList)

}
