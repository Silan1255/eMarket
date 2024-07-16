package com.example.emarket.ui.cart.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emarket.data.model.CartProductList
import com.example.emarket.data.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartVM @Inject constructor(private val repository: CartRepository) : ViewModel() {
    val products: LiveData<List<CartProductList>> = repository.allProducts

    fun delete(product: CartProductList) = viewModelScope.launch {
        repository.delete(product)
    }

    fun addProduct(product: CartProductList) {
        viewModelScope.launch {
            val existingProduct = repository.getProductByName(product.name)
            if (existingProduct != null) {
                val updatedProduct = existingProduct.copy(quantity = existingProduct.quantity + 1)
                repository.update(updatedProduct)
            } else {
                repository.insert(product)
            }
        }
    }

    fun updateProductQuantity(product: CartProductList) {
        viewModelScope.launch {
            if (product.quantity > 0) {
                repository.update(product)
            } else {
                repository.delete(product)
            }
        }
    }
}
