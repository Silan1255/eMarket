package com.example.emarket.ui.main.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.emarket.MainActivity
import com.example.emarket.R
import com.example.emarket.data.model.CartProductList
import com.example.emarket.data.model.FavoriteProductList
import com.example.emarket.data.model.ProductList
import com.example.emarket.databinding.FragmentMainBinding
import com.example.emarket.ui.cart.viewModel.CartVM
import com.example.emarket.ui.favorite.viewModel.FavoriteVM
import com.example.emarket.ui.filter.view.FilterFragment
import com.example.emarket.ui.main.adapter.EMarketAdapter
import com.example.emarket.ui.main.adapter.EMarketListener
import com.example.emarket.ui.main.viewModel.MainVM
import com.example.emarket.utils.FilterCriteria
import com.example.emarket.utils.custom.CustomProgressBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), EMarketListener, FilterFragment.FilterListener {

    //region variables
    private lateinit var eMarketVM: MainVM
    private lateinit var cartVM: CartVM
    private lateinit var favoriteVM: FavoriteVM
    private lateinit var eMarkets: ArrayList<ProductList>
    private lateinit var eMarketAdapter: EMarketAdapter
    private lateinit var binding: FragmentMainBinding
    private lateinit var progressBar: CustomProgressBar
    private var currentFilterCriteria: String = ""
    private var currentFilterBrands: List<String> = listOf()
    private var currentFilterModels: List<String> = listOf()
    private var currentPage = 1
    private val pageSize = 4

    //endregion

    //region lifecycle
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        addListeners()
        getEMarket()
    }

    override fun onStart() {
        super.onStart()
        setObservers()
    }

    override fun onStop() {
        super.onStop()
        removeObservers()
    }
    //endregion

    //region init
    private fun init() {
        progressBar = CustomProgressBar(requireContext())
        favoriteVM = ViewModelProvider(this)[FavoriteVM::class.java]
        eMarketVM = ViewModelProvider(this)[MainVM::class.java]
        cartVM = ViewModelProvider(this)[CartVM::class.java]
        eMarkets = arrayListOf()
        eMarketAdapter = EMarketAdapter(requireContext(), this)
        binding.rvCards.layoutManager = GridLayoutManager(context, 2)
        binding.rvCards.adapter = eMarketAdapter

    }

    private fun initViews() {
        if (eMarkets.isEmpty()) {
            binding.noData.root.visibility = View.VISIBLE
            binding.rvCards.visibility = View.GONE
            binding.noData.txtNoData.text = getString(R.string.no_products_yet)

        } else {
            binding.noData.root.visibility = View.GONE
            binding.rvCards.visibility = View.VISIBLE
        }
    }

    //endregion

    //region tool
    private fun getEMarket() {
        progressBar.show()
        eMarketVM.getEMarket()
        eMarketVM.eMarketData.observe(viewLifecycleOwner, eMarketDataObserver)
    }

    override fun onEMarketClicked(productList: ProductList) {
        val action = MainFragmentDirections.actionMainFragmentToProductDetailsFragment(
            productList.price!!,
            productList.name!!,
            productList.description!!,
            productList.image!!
        )
        findNavController().navigate(action)
    }

    override fun onAddToCartClicked(productList: ProductList) {
        val product =
            CartProductList(name = productList.name!!, price = productList.price!!, quantity = 1)
        val existingProduct = cartVM.products.value?.find { it.name == product.name }

        if (existingProduct != null) {
            val updatedProduct = existingProduct.copy(quantity = existingProduct.quantity + 1)
            cartVM.updateProductQuantity(updatedProduct)
        } else {
            cartVM.addProduct(product)
        }
    }


    override fun onFavoriteClicked(productList: ProductList) {
        val product =
            FavoriteProductList(name = productList.name!!, price = productList.price!!, image = productList.image!!)
        favoriteVM.insert(product)

    }


    private fun showFilterDialog() {
        val dialog = FilterFragment()
        dialog.setFilterListener(this)
        dialog.show(parentFragmentManager, "FilterDialog")
    }

    override fun onFilterApplied(criteria: String, brands: List<String>, models: List<String>) {
        val filteredList = if (brands.isEmpty() && models.isEmpty() && criteria.isEmpty()) {
            eMarkets
        } else {
            eMarkets.filter { product ->
                (criteria == FilterCriteria.NEW_TO_OLD.criteria && product.createdAt != null) ||
                        (criteria == FilterCriteria.OLD_TO_NEW.criteria && product.createdAt != null) ||
                        (brands.isEmpty() || brands.contains(product.brand)) &&
                        (models.isEmpty() || models.contains(product.model))
            }.sortedWith(when (criteria) {
                FilterCriteria.NEW_TO_OLD.criteria -> compareByDescending { it.createdAt }
                FilterCriteria.OLD_TO_NEW.criteria  -> compareBy { it.createdAt }
                FilterCriteria.PRICE_HIGH_TO_LOW.criteria  -> compareByDescending { it.price }
                FilterCriteria.PRICE_LOW_TO_HIGH.criteria -> compareBy { it.price }
                else -> compareBy { it.name }
            })
        }

        eMarketAdapter.submitList(filteredList)
    }

    private fun filterAndDisplayList() {
        val filteredList =
            if (currentFilterBrands.isEmpty() && currentFilterModels.isEmpty() && currentFilterCriteria.isEmpty()) {
                eMarkets
            } else {
                eMarkets.filter { product ->
                    (currentFilterBrands.isEmpty() || currentFilterBrands.contains(product.brand)) &&
                            (currentFilterModels.isEmpty() || currentFilterModels.contains(product.model))
                }.sortedWith(when (currentFilterCriteria) {
                    "New_to_old" -> compareByDescending { it.createdAt }
                    "Old_to_new" -> compareBy { it.createdAt }
                    "Price_hight_to_low" -> compareByDescending { it.price }
                    "Price_low_to_hight" -> compareBy { it.price }
                    else -> compareBy { it.name }
                })
            }
        eMarketAdapter.submitList(filteredList)
    }

    private fun loadMoreProducts() {
        val startIndex = currentPage * pageSize
        val endIndex = startIndex + pageSize
        val newProducts = eMarkets.subList(startIndex, minOf(endIndex, eMarkets.size))
        eMarketAdapter.addItems(newProducts)
        currentPage++
        Log.d("MainFragment", "Loading page: $currentPage")
    }
    //endregion

    //region listeners
    private fun addListeners() {
        binding.btnSelectFilter.setOnClickListener {
            binding.edtSearch.text?.clear()
            showFilterDialog()
        }
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val searchText = s.toString()
                val filteredList = if (searchText.isEmpty()) {
                    filterAndDisplayList()
                    eMarkets
                } else {
                    eMarkets.filter { it.name?.contains(searchText, ignoreCase = true) == true }
                }
                eMarketAdapter.submitList(filteredList)
            }
        })

        binding.rvCards.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    loadMoreProducts()
                }
            }
        })
    }

    private fun setObservers() {
        eMarketVM.eMarketData.observe(viewLifecycleOwner, eMarketDataObserver)
    }

    private fun removeObservers() {
        eMarketVM.eMarketData.removeObserver(eMarketDataObserver)
    }
    //endregion


    //region data
    private val eMarketDataObserver = Observer<List<ProductList>> { response ->
        progressBar.dismiss()
        eMarkets.clear()
        eMarkets.addAll(response)
        eMarketAdapter.submitList(eMarkets)
        initViews()
        (activity as MainActivity).updateCartBadge()

    }
    //endregion
}


