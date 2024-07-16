package com.example.emarket.ui.filter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.emarket.R

class ModelAdapter(private val stringList: List<String>,  private val selectedModels: MutableList<String>) :
    RecyclerView.Adapter<ModelAdapter.StringViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_model_list, parent, false)
        return StringViewHolder(view)
    }

    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        holder.itemText.text = stringList[position]
        val model = stringList[position]
        holder.bind(model)
    }

    override fun getItemCount() = stringList.size

    inner class StringViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val checkBox: CheckBox = itemView.findViewById(R.id.cb_model)
        val itemText: TextView = itemView.findViewById(R.id.txt_model)

        fun bind(model: String) {
            checkBox.text = model
            checkBox.isChecked = selectedModels.contains(model)

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedModels.add(model)
                } else {
                    selectedModels.remove(model)
                }
            }
        }
    }
}
