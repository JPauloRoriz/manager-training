package com.example.managertraining.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrainingModel(
    val id: String = "",
    val idUser: String? = "",
    val name: String = "",
    val description: String = "",
    val data: String = "",
    var isEmpty: Boolean = true
) : Parcelable
