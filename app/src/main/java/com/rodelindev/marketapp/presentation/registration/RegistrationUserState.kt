package com.rodelindev.marketapp.presentation.registration

import com.rodelindev.marketapp.data.remote.model.ShoppingDto
import com.rodelindev.marketapp.domain.model.Gender
import com.rodelindev.marketapp.domain.model.User

data class RegistrationUserState(
    val isLoading:Boolean=false,
    val error:String?=null,
    val genders:List<Gender>? = null,
    val createAccount: User?=null
)
