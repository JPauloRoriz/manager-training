package com.example.managertraining.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class UserModel(
    var id: String? = null,
    val name : String = "",
    val email : String = "",
    val trainings : List<TrainingModel> = listOf()
) : Parcelable