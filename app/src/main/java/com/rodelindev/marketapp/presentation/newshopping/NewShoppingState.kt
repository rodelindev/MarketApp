package com.rodelindev.marketapp.presentation.newshopping

import com.rodelindev.marketapp.data.remote.model.ShoppingDto

data class NewShoppingState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val createShopping: ShoppingDto? = null,
)