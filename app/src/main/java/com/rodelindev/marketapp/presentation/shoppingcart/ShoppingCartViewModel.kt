package com.rodelindev.marketapp.presentation.shoppingcart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodelindev.marketapp.data.database.model.DBProduct
import com.rodelindev.marketapp.data.repositories.ProductRepository
import com.rodelindev.marketapp.presentation.product.ProductState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingCartViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ShoppingState())
    val state: StateFlow<ShoppingState> get() = _state

    init {
        getShoppingProduct()
    }

    private fun getShoppingProduct() {
        viewModelScope.launch {
            try {
                productRepository.getAllLocalProduct().onEach { productList ->
                    _state.update { state ->
                        state.copy(products = productList)
                    }
                }.stateIn(viewModelScope)
            }catch (ex:java.lang.Exception){
                _state.update { it.copy(error = ex.message) }
            }finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }


    suspend fun deleteShoppingProduct(dbProduct: DBProduct) {
        productRepository.deleteProduct(dbProduct)
    }

}