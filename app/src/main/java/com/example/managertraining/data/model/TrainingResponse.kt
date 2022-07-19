package com.example.managertraining.data.model

import com.example.managertraining.domain.model.TrainingModel

data class TrainingResponse(
    val id: String = "",
    val idUser: String? = "",
    val name: String = "",
    val description: String = "",
    val data: String = ""
)
