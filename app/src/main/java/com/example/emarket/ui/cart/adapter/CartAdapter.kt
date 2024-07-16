package com.example.emarket.ui.cart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.emarket.R
import com.example.emarket.data.model.CartProductList

class CartAdapter( val listener: OnQuantityClicked) : ListAdapter<CartProductList, CartAdapter.CartViewHolder>(CartDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart_list, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

 inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.txt_product_name)
        private val price: TextView = itemView.findViewById(R.id.txr_product_price)
        private val btnMinus: AppCompatButton = itemView.findViewById(R.id.btn_minus)
        private val btnQuantity: AppCompatButton = itemView.findViewById(R.id.btn_quantity)
        private val btnPlus: AppCompatButton = itemView.findViewById(R.id.btn_plus)

        fun bind(cartItem: CartProductList) {
            name.text = cartItem.name
            btnQuantity.text = cartItem.quantity.toString()

            val totalPrice = "${cartItem.price.toDouble() * cartItem.quantity}"
            price.text = totalPrice

            btnPlus.setOnClickListener {
                listener.onQuantityUpClicked(cartItem)
            }

            btnMinus.setOnClickListener {
                listener.onQuantityDownClicked(cartItem)
            }
        }
    }
}

interface OnQuantityClicked {
    fun onQuantityUpClicked(productList: CartProductList)
    fun onQuantityDownClicked(productList: CartProductList)
}

class CartDiffCallback : DiffUtil.ItemCallback<CartProductList>() {
    override fun areItemsTheSame(oldItem: CartProductList, newItem: CartProductList): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CartProductList, newItem: CartProductList): Boolean {
        return oldItem == newItem
    }
}
