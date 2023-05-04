package com.rodelindev.marketapp.presentation.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodelindev.marketapp.data.Result
import com.rodelindev.marketapp.data.repositories.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel@Inject constructor(private val repository: CategoryRepository) : ViewModel() {

    private val _state = MutableStateFlow(CategoriesState())
    val state : StateFlow<CategoriesState> = _state

    init{
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {

            _state.update { it.copy(isLoading = true) }

            try{
                repository.categories.onEach {
                    _state.update {state ->
                        state.copy( categories = it)
                    }
                }.stateIn(viewModelScope)
            }catch (ex:java.lang.Exception){
                _state.update { it.copy(error = ex.message) }
            }finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onUIReady(){
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            repository.requestCategory()
            _state.update { it.copy(isLoading = false) }
        }
    }
}
