package com.rodelindev.marketapp.presentation.login

import com.rodelindev.marketapp.domain.model.User

data class LoginState(
    val user: User? = null,
    val loader: Boolean? = false,
    val error: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
)
