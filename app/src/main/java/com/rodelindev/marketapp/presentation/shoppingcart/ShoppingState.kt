package com.rodelindev.marketapp.presentation.shoppingcart

import com.rodelindev.marketapp.data.database.model.DBProduct

data class ShoppingState(
    val products: List<DBProduct>? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isEmpty: Boolean = false
)
