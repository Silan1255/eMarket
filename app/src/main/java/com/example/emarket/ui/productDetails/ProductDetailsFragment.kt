package com.example.emarket.ui.productDetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.emarket.R
import com.example.emarket.data.model.CartProductList
import com.example.emarket.databinding.FragmentProductDetailsBinding
import com.example.emarket.ui.cart.viewModel.CartVM
import com.example.emarket.utils.ProductDetails
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailsBinding

    private var price: String? = null
    private var name: String? = null
    private var description: String? = null
    private var image: String? = null
    private lateinit var cartVM: CartVM

    //region lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        addListeners()

    }
    //endregion

    //region init
    private fun init() {
        cartVM = ViewModelProvider(this)[CartVM::class.java]
        price = arguments?.getString(ProductDetails.PRICE.data) ?: ""
        name = arguments?.getString(ProductDetails.NAME.data) ?: ""
        description = arguments?.getString(ProductDetails.DESCRIPTION.data) ?: ""
        image = arguments?.getString(ProductDetails.IMAGE.data) ?: ""
        initViews()
    }

    @SuppressLint("CheckResult")
    private fun initViews() {
        binding.txtDescription.text = description
        Glide.with(requireContext()).load(image).into(binding.imgProduct)
        binding.price.text = price
        binding.txtTitle.text = name
        binding.txtHeader.text = name
    }
    //endregion

    //region listeners
    private fun addListeners() {
        binding.goBack.setOnClickListener {
            findNavController().navigate(R.id.action_productDetailsFragment_to_mainFragment)
        }
        binding.btnAddCart.setOnClickListener {
            val product = CartProductList(name = name!!, price = price!!, quantity = 1)
            cartVM.addProduct(product)
        }
    }
    //endregion
}