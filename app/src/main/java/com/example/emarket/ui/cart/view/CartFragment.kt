package com.example.emarket.ui.cart.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emarket.MainActivity
import com.example.emarket.R
import com.example.emarket.data.model.CartProductList
import com.example.emarket.databinding.FragmentCartBinding
import com.example.emarket.ui.cart.adapter.CartAdapter
import com.example.emarket.ui.cart.adapter.OnQuantityClicked
import com.example.emarket.ui.cart.viewModel.CartVM
import com.example.emarket.utils.custom.CustomProgressBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(), OnQuantityClicked {

    //region variables
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private var totalAmount = 0.0
    private val cartViewModel: CartVM by viewModels()
    private lateinit var cartAdapter: CartAdapter
    private lateinit var progressBar: CustomProgressBar
    //endregion

    //region lifecycle
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observeCartItems()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    //endregion

    //region init
    private fun init() {
        progressBar = CustomProgressBar(requireContext())
        cartAdapter = CartAdapter(this)
        binding.recyclerViewCard.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewCard.adapter = cartAdapter
        progressBar.show()
    }

    private fun initViews(cartItems: List<CartProductList>) {
        if (cartItems.isEmpty()) {
            binding.noData.root.visibility = View.VISIBLE
            binding.recyclerViewCard.visibility = View.GONE
            binding.noData.txtNoData.text = getString(R.string.no_products_your_cart_yet)

        } else {
            binding.noData.root.visibility = View.GONE
            binding.recyclerViewCard.visibility = View.VISIBLE
        }
    }
    //endregion

    //region tool

    override fun onQuantityUpClicked(productList: CartProductList) {
        cartViewModel.updateProductQuantity(productList.copy(quantity = productList.quantity + 1))
        totalAmount += productList.price.toDouble()
        binding.txtPrice.text = getString(R.string.total_price_format, totalAmount)
    }

    override fun onQuantityDownClicked(productList: CartProductList) {
        if (productList.quantity > 1) {
            cartViewModel.updateProductQuantity(productList.copy(quantity = productList.quantity - 1))
            totalAmount -= productList.price.toDouble()
        } else {
            cartViewModel.delete(productList)
            totalAmount -= productList.price.toDouble() * productList.quantity
        }
        binding.txtPrice.text = getString(R.string.total_price_format, totalAmount)
    }

    private fun updateTotalPrice(cartItems: List<CartProductList>) {
        totalAmount = cartItems.sumOf { it.price.toDouble() * it.quantity }
        binding.txtPrice.text = getString(R.string.total_price_format, totalAmount)
    }
    //endregion

    //region data
    private fun observeCartItems() {
        cartViewModel.products.observe(viewLifecycleOwner, Observer { cartItems ->
            progressBar.dismiss()
            cartAdapter.submitList(cartItems)
            updateTotalPrice(cartItems)
            initViews(cartItems)
            (activity as MainActivity).updateCartBadge()
        })
    }
    //endregion
}
