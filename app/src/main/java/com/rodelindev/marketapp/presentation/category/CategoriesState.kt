package com.rodelindev.marketapp.presentation.category

import com.rodelindev.marketapp.domain.model.Category

data class CategoriesState(
    val isLoading:Boolean = false,
    val error:String? = null,
    val categories:List<Category>? = null,
    val isEmpty:Boolean=false
)
