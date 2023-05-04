package com.rodelindev.marketapp.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodelindev.marketapp.data.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import com.rodelindev.marketapp.data.Result

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProductState())
    val state: StateFlow<ProductState> get() = _state

    fun populateProducts(idCategory: String) {

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            try {
                productRepository.populateProducts(idCategory).catch {

                }.onEach { result ->
                    when (result) {
                        is Result.Error -> {
                            _state.update { it.copy(error = result.message) }
                        }
                        is Result.Success -> {
                            _state.update { it.copy(success = result.data) }
                        }
                    }
                }.stateIn(viewModelScope)

            } catch (ex: Exception) {
                _state.update { it.copy(error = ex.message) }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }

        }

    }
}
