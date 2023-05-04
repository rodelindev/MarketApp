package com.rodelindev.marketapp.data.repositories

import com.rodelindev.marketapp.data.Result
import com.rodelindev.marketapp.data.datasource.UserDataSource
import com.rodelindev.marketapp.data.datasource.UserDataSourceImp
import com.rodelindev.marketapp.data.remote.model.CreateAccountRequest
import com.rodelindev.marketapp.data.remote.model.LoginRemote
import com.rodelindev.marketapp.domain.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDataSource: UserDataSource
) {

    /*private val userDataSource = UserDataSourceImp()*/

    suspend fun signIn(email: String, password: String): Result<User> = userDataSource.signIn(email, password)

    suspend fun createAccount(createAccountRequest: CreateAccountRequest) = userDataSource.createAccount(createAccountRequest)
}