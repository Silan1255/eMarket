package com.example.emarket.ui.favorite.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emarket.data.model.FavoriteProductList
import com.example.emarket.data.repository.FavoriteProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteVM @Inject constructor(private val repository: FavoriteProductRepository) : ViewModel() {
    val products: LiveData<List<FavoriteProductList>> = repository.allProducts

    fun insert(product: FavoriteProductList) = viewModelScope.launch {
        repository.insert(product)
    }

    fun delete(product: FavoriteProductList) = viewModelScope.launch {
        repository.delete(product)
    }

}
