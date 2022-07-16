package com.example.managertraining.domain.model

import java.io.Serializable

data class UserModel(
    var id: String? = null,
    val name : String = "",
    val email : String = "",
    val trainings : List<TrainingModel> = listOf()
) : Serializable