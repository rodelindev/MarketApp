package com.rodelindev.marketapp.data.datasource

import com.rodelindev.marketapp.data.Result
import com.rodelindev.marketapp.data.remote.model.CreateAccountRequest
import com.rodelindev.marketapp.data.remote.model.LoginRemote
import com.rodelindev.marketapp.domain.model.User

interface UserDataSource {

    suspend fun signIn(email: String, password: String): Result<User>

    suspend fun createAccount(createAccountRequest: CreateAccountRequest): Result<User>

}
