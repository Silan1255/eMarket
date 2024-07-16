package com.example.emarket.data.api

import com.example.emarket.data.model.ProductList
import com.example.emarket.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(Constants.BASE_URL + "products")
    suspend fun getEMarket(): ArrayList<ProductList>
}