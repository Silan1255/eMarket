package com.example.emarket.data.repository

import com.example.emarket.data.api.ApiService
import com.example.emarket.data.model.ProductList
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getEMarket(): ArrayList<ProductList> {
        return apiService.getEMarket()
    }
}