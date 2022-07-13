package com.example.managertraining.domain.model

import java.io.Serializable

data class UserDomain(
    var id: String? = null,
    val name : String = "",
    val email : String = "",
    val password : String = ""
) : Serializable