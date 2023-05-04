package com.rodelindev.marketapp.domain.usescases.authenticate_user

import com.rodelindev.marketapp.data.Result
import com.rodelindev.marketapp.data.repositories.UserRepository
import com.rodelindev.marketapp.domain.model.User
import javax.inject.Inject

class AuthenticateUser @Inject constructor(
    private val userRepository: UserRepository
) {


    suspend operator fun invoke(email: String, password: String): Result<User> {
        return userRepository.signIn(email, password)
    }
}