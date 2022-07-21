package com.example.managertraining.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseModel(
    val id: String = "",
    val idTraining: String = "",
    val name: String = "",
    val image: String = "",
    val note: String = ""
) : Parcelable
