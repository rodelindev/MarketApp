package com.rodelindev.marketapp.domain.usescases

data class ValidateResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
