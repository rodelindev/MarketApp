package com.rodelindev.marketapp.data.repositories

import com.rodelindev.marketapp.data.datasource.GenderDataSource
import javax.inject.Inject

class GenderRepository @Inject constructor(private val genderDataSource: GenderDataSource) {

    suspend fun getGenders() = genderDataSource.populateGenders()

}
