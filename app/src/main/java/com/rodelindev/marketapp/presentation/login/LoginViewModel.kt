package com.rodelindev.marketapp.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodelindev.marketapp.data.Result
import com.rodelindev.marketapp.domain.usescases.UseCaseLogin
import com.rodelindev.marketapp.domain.usescases.authenticate_user.AuthenticateUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCasesLogin: UseCaseLogin
) : ViewModel() {

    private val _state = MutableLiveData<LoginState>()
    val state: LiveData<LoginState> = _state

    fun signIn(email: String, password: String) {

        val emailResult = useCasesLogin.validateEmail(email)
        val passwordResult = useCasesLogin.validatePassword(password)

        if (!emailResult.successful) {
            _state.value = LoginState().copy(emailError = emailResult.errorMessage)
            return
        }

        if (!passwordResult.successful) {
            _state.value = LoginState().copy(emailError = passwordResult.errorMessage)
            return
        }

        viewModelScope.launch {

            try {
                _state.value = LoginState().copy(loader = true)

                val response = withContext(Dispatchers.IO) {
                    useCasesLogin.authenticateUser(email, password)
                }
                when (response) {
                    is Result.Success -> {
                        response.data?.let { user ->
                            _state.value = LoginState().copy(user = user)
                        }
                    }
                    is Result.Error -> {
                        response.data?.let {
                            _state.value = LoginState().copy(
                                error = response.message
                            )
                        }
                    }
                }
            } catch (ex: java.lang.Exception) {
                _state.value = LoginState().copy(
                    error = ex.message
                )
            } finally {
                _state.value = LoginState().copy(
                    loader = false
                )
            }
        }
    }


    /*val request = Api.build().signIn(LoginRequest(email, password))
            request.enqueue(object : Callback<LoginRemote> {
                override fun onResponse(call: Call<LoginRemote>, response: Response<LoginRemote>) {
                    if (response.isSuccessful) {
                        val loginRemote = response.body()

                        loginRemote?.let { user ->
                            print("Bienvenido: ${user.data.nombres}  ${user.data.apellidos}")
                        }
                    }
                }

                override fun onFailure(call: Call<LoginRemote>, t: Throwable) {
                    print(t.message)
                }
            })*/
}