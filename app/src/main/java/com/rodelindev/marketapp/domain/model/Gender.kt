package com.rodelindev.marketapp.domain.model

import com.rodelindev.marketapp.data.remote.model.GenderRemote

data class Gender(
    val gender:String,
    val description:String
){
    override fun toString(): String {
        return "$description"
    }
}

fun List<GenderRemote>.toListGenders() : List<Gender> = map {
    Gender(gender = it.gender, description = it.description)
}

