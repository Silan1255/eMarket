package com.example.emarket.utils

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.text.SimpleDateFormat
import java.util.Locale

object Utils {
    private var loggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    var client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    fun getFormattedDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        return outputFormat.format(date)
    }

}