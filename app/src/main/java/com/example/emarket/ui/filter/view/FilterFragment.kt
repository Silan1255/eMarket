package com.example.emarket.ui.filter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emarket.R
import com.example.emarket.databinding.FragmentFilterBinding
import com.example.emarket.ui.filter.adapter.BrandAdapter
import com.example.emarket.ui.filter.adapter.ModelAdapter
import com.example.emarket.utils.Constants
import com.example.emarket.utils.FilterCriteria
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterFragment : DialogFragment() {

    //region variables
    private lateinit var binding: FragmentFilterBinding
    private lateinit var brandList: List<String>
    private lateinit var modelList: List<String>
    private var listener: FilterListener? = null
    private lateinit var selectedBrands: MutableList<String>
    private lateinit var selectedModels: MutableList<String>
    //endregion

    //region lifecycle
    interface FilterListener {
        fun onFilterApplied(criteria: String, brands: List<String>, models: List<String>)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListeners()
        init()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setBackgroundDrawableResource(android.R.color.white)
    }
    //endregion

    //region init
    private fun init() {
        brandList = Constants.brandList
        modelList = Constants.modelList
        selectedBrands = mutableListOf()
        selectedModels = mutableListOf()
        binding.recyclerViewBrand.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewBrand.adapter = BrandAdapter(brandList, selectedBrands)
        binding.recyclerViewModel.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewModel.adapter = ModelAdapter(modelList, selectedModels)
    }
    //endregion

    //region listeners
    fun setFilterListener(listener: FilterListener) {
        this.listener = listener
    }

    private fun addListeners() {

        binding.btnClose.setOnClickListener {
            dismiss()
        }
        binding.btnPrimary.setOnClickListener {
            val selectedCriteria = when (binding.radioGroup.checkedRadioButtonId) {
                R.id.rb_new_to_old -> FilterCriteria.NEW_TO_OLD.criteria
                R.id.rb_old_to_new ->  FilterCriteria.OLD_TO_NEW.criteria
                R.id.rb_price_hight_to_low -> FilterCriteria.PRICE_HIGH_TO_LOW.criteria
                R.id.rb_price_low_to_hight -> FilterCriteria.PRICE_LOW_TO_HIGH.criteria
                else -> ""
            }
            listener?.onFilterApplied(selectedCriteria, selectedBrands, selectedModels)
            dismiss()
        }
    }
    //endregion
}
