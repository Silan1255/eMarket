package com.example.emarket.utils

enum class FilterCriteria(val criteria: String) {
    NEW_TO_OLD("New_to_old"),
    OLD_TO_NEW("Old_to_new"),
    PRICE_HIGH_TO_LOW("Price_hight_to_low"),
    PRICE_LOW_TO_HIGH("Price_low_to_hight"),
}

enum class ProductDetails(val data: String) {
    PRICE("price"),
    NAME("name"),
    DESCRIPTION("description"),
    IMAGE("image"),
}
