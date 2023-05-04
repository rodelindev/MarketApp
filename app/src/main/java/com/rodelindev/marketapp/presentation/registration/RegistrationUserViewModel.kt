package com.rodelindev.marketapp.presentation.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodelindev.marketapp.data.remote.model.CreateAccountRequest
import com.rodelindev.marketapp.data.repositories.GenderRepository
import com.rodelindev.marketapp.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.rodelindev.marketapp.data.Result

@HiltViewModel
class RegistrationUserViewModel @Inject
constructor(
    private val genderRepository: GenderRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableLiveData<RegistrationUserState>()
    val state: LiveData<RegistrationUserState> = _state

    init {
        getGenders()
    }

    private fun getGenders() {

        viewModelScope.launch {

            try {

                _state.value = RegistrationUserState().copy(isLoading = true)

                val response = withContext(Dispatchers.IO) {
                    genderRepository.getGenders()
                }

                when (response) {
                    is Result.Error -> {
                        _state.value = RegistrationUserState().copy(error = response.message)
                    }
                    is Result.Success -> {
                        _state.value = RegistrationUserState().copy(genders = response.data)
                    }
                }

            } catch (ex: java.lang.Exception) {
                _state.value = RegistrationUserState().copy(error = ex.message)
            } finally {
                _state.value = RegistrationUserState().copy(isLoading = false)
            }
        }

    }

    fun createAccount(
        names: String,
        lastName: String,
        email: String,
        phone: String,
        document: String,
        password: String,
        genderCode: String
    ) {

        viewModelScope.launch {

            try {

                _state.value = RegistrationUserState().copy(isLoading = true)

                val response = withContext(Dispatchers.IO) {
                    userRepository.createAccount(
                        CreateAccountRequest(
                            names,
                            lastName,
                            email,
                            password,
                            phone,
                            genderCode,
                            document
                        )
                    )
                }

                when (response) {
                    is Result.Error -> {
                        _state.value = RegistrationUserState().copy(error = response.message)
                    }
                    is Result.Success -> {
                        _state.value = RegistrationUserState().copy(createAccount = response.data)
                    }
                }

            } catch (ex: java.lang.Exception) {
                _state.value = RegistrationUserState().copy(error = ex.message)
            } finally {
                _state.value = RegistrationUserState().copy(isLoading = false)
            }
        }
    }
}
