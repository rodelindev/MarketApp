package com.rodelindev.marketapp.domain.usescases

import com.rodelindev.marketapp.domain.usescases.authenticate_user.AuthenticateUser
import com.rodelindev.marketapp.domain.usescases.validate_email.ValidateEmail
import com.rodelindev.marketapp.domain.usescases.validate_password.ValidatePassword

data class UseCaseLogin(
    val validateEmail: ValidateEmail,
    val validatePassword: ValidatePassword,
    val authenticateUser: AuthenticateUser
)