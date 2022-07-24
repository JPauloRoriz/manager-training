package com.example.managertraining.domain.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseModel(
    val id: String = "",
    val idTraining: String = "",
    val name: String = "",
    var image: String = "",
    val note: String = ""
) : Parcelable
