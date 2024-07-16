package com.example.emarket.ui.main.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emarket.data.model.ProductList
import com.example.emarket.data.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(private val repository: ApiRepository) : ViewModel() {

    var eMarketData: MutableLiveData<ArrayList<ProductList>> = MutableLiveData()

    fun getEMarket() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getEMarket()
            eMarketData.postValue(response)
        }
    }
}