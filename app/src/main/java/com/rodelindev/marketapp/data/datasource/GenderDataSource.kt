package com.rodelindev.marketapp.data.datasource

import com.rodelindev.marketapp.domain.model.Gender
import com.rodelindev.marketapp.data.Result

interface GenderDataSource {

    suspend fun populateGenders(): Result<List<Gender>>
}