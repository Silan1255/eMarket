package com.example.emarket.ui.favorite.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.emarket.R
import com.example.emarket.data.model.FavoriteProductList
import com.example.emarket.databinding.FragmentFavoriteBinding
import com.example.emarket.ui.favorite.adapter.FavoriteAdapter
import com.example.emarket.ui.favorite.adapter.OnFavoriteClicked
import com.example.emarket.ui.favorite.viewModel.FavoriteVM
import com.example.emarket.utils.custom.CustomProgressBar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoriteFragment : Fragment(), OnFavoriteClicked {

    //region variables
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var favoriteVM: FavoriteVM
    private lateinit var progressBar: CustomProgressBar
    //endregion

    //region lifecycle
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observeCartItems()
    }
    //endregion

    //region init
    private fun init() {
        progressBar = CustomProgressBar(requireContext())
        favoriteVM = ViewModelProvider(this)[FavoriteVM::class.java]
        favoriteAdapter = FavoriteAdapter(this, requireContext())
        binding.rvFavoriteProduct.layoutManager = GridLayoutManager(context, 2)
        binding.rvFavoriteProduct.adapter = favoriteAdapter
        progressBar.show()
    }

    private fun initViews(cartItems: List<FavoriteProductList>) {
        if (cartItems.isEmpty()) {
            binding.noData.root.visibility = View.VISIBLE
            binding.rvFavoriteProduct.visibility = View.GONE
            binding.noData.txtNoData.text = getString(R.string.no_products_your_favorite_yet)

        } else {
            binding.noData.root.visibility = View.GONE
            binding.rvFavoriteProduct.visibility = View.VISIBLE
        }
    }
    //endregion

    //region tool
    override fun onFavoriteClicked(favoriteProductList: FavoriteProductList) {
        favoriteVM.delete(favoriteProductList)
    }
    //endregion

    //region data
    private fun observeCartItems() {
        favoriteVM.products.observe(viewLifecycleOwner, Observer { cartItems ->
            favoriteAdapter.submitList(cartItems)
            progressBar.dismiss()
            initViews(cartItems)
        })
    }
    //endregion
}