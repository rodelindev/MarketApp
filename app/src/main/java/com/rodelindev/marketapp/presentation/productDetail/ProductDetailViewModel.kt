package com.rodelindev.marketapp.presentation.productDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodelindev.marketapp.data.database.model.DBProduct
import com.rodelindev.marketapp.data.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productRepository: ProductRepository
): ViewModel() {

    suspend fun saveLocalProduct(dbProduct: DBProduct) = viewModelScope.launch {
        productRepository.saveLocalProduct(dbProduct)
    }
}