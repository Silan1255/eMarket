package com.example.emarket.ui.filter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.emarket.R
import com.example.emarket.databinding.ItemBrandListBinding
import com.example.emarket.ui.main.adapter.EMarketAdapter

class BrandAdapter(
    private val stringList: List<String>,
    private val selectedBrands: MutableList<String>
) :
    RecyclerView.Adapter<BrandAdapter.StringViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_brand_list, parent, false)
        return StringViewHolder(view)
    }

    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        val brand = stringList[position]
        holder.itemText.text = stringList[position]
        holder.bind(brand)
    }

    override fun getItemCount() = stringList.size


    inner class StringViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkbox_brand)
        val itemText: TextView = itemView.findViewById(R.id.txt_brand)

        fun bind(brand: String) {
            checkBox.text = brand
            checkBox.isChecked = selectedBrands.contains(brand)

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedBrands.add(brand)
                } else {
                    selectedBrands.remove(brand)
                }
            }
        }
    }
}




