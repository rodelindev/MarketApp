package com.rodelindev.marketapp.domain.model

import com.rodelindev.marketapp.data.remote.model.UserRemote

data class User(
    val uuid: String,
    val names: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val gender: String,
    val document: String,
    val type: String,
)

fun UserRemote.toUser(): User {
    return User(
        uuid = uuid,
        names = names,
        lastName = lastName,
        email = email,
        phone = phone,
        gender = gender,
        document = document,
        type = type
    )
}
