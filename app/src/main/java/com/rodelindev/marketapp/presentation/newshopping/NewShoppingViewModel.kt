package com.rodelindev.marketapp.presentation.newshopping

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodelindev.marketapp.data.Result
import com.rodelindev.marketapp.data.database.model.DBProduct
import com.rodelindev.marketapp.data.database.model.toProductRequest
import com.rodelindev.marketapp.data.remote.model.PayMethod
import com.rodelindev.marketapp.data.remote.model.ShippingAddress
import com.rodelindev.marketapp.data.remote.model.ShippingRequest
import com.rodelindev.marketapp.data.repositories.NewShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class NewShoppingViewModel @Inject constructor(
    private val newShoppingRepository: NewShoppingRepository,
) : ViewModel() {

    private val _state = MutableLiveData<NewShoppingState>()
    val state: LiveData<NewShoppingState> = _state


    private val _payState = MutableStateFlow<Double?>(null)
    val payState: StateFlow<Double?> = _payState


    private var products: List<DBProduct> = listOf()


    init {
        getShoppingProduct()
    }

    private fun getShoppingProduct() {
        viewModelScope.launch {
            newShoppingRepository.getAllLocalProduct().onEach { productList ->
                products = productList
                _payState.value = productList.sumOf { it.totalPay }
            }.stateIn(viewModelScope)
        }
    }

    fun newShopping(
        type: Int,
        address: String,
        reference: String,
        district: String,
        typeMethod: Int,
        amount: String,
    ) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    newShoppingRepository.getNewShopping(
                        ShippingRequest(
                            shippingAddress = ShippingAddress(
                                type = type,
                                address = address,
                                reference = reference,
                                district = district
                            ),
                            payMethod = PayMethod(
                                typeMethod = typeMethod,
                                amount = amount.toInt()
                            ),
                            dateTime = LocalDateTime.now()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                            products = products.toProductRequest(),
                            total = products.sumOf { it.totalPay }.toInt()
                        )
                    )
                }
                when (response) {
                    is Result.Error -> {
                        _state.value = NewShoppingState(error = response.message)
                    }

                    is Result.Success -> {
                        _state.value = NewShoppingState(createShopping = response.data)
                    }
                }
            } catch (ex: java.lang.Exception) {
                _state.value = NewShoppingState().copy(error = ex.message)
            } finally {
                _state.value = NewShoppingState().copy(isLoading = false)
            }
        }
    }
}